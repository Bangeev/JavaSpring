package exodia.web.controllers;

import exodia.domain.models.view.DocumentHomeViewModel;
import exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class HomeController {

    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
   public ModelAndView modelAndView(ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") !=null){
            modelAndView.setViewName("redirect:/home");
        }else {
            modelAndView.setViewName("index");
        }



    return modelAndView;

    }
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView,HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {
            List<DocumentHomeViewModel> documents =this.documentService.findAllDocuments().
                    stream()
                    .map(doc-> this.modelMapper.map(doc, DocumentHomeViewModel.class))
                    .map(doc -> {
                if (doc.getTitle().length() > 12){
                    doc.setTitle(doc.getTitle().substring(0, 11) + "...");
                }
                return doc;
            })
                            .collect(Collectors.toList());

            modelAndView.setViewName("home");
            modelAndView.addObject("documents", documents);
        }

    return modelAndView;
    }

}
