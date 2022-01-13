package exodia.web.controllers;

import exodia.domain.models.binding.DocumentScheduleBindingModel;
import exodia.service.DocumentService;
import exodia.domain.models.service.DocumentServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {
            modelAndView.setViewName("schedule");
        }
        return modelAndView;


    }
    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(@ModelAttribute DocumentScheduleBindingModel model, ModelAndView modelAndView){
        DocumentServiceModel documentServiceModel = this.documentService.scheduleDocument(this.modelMapper.map(model, DocumentServiceModel.class));

        if (documentServiceModel == null){
            throw new IllegalArgumentException("Document creation failed!");
        }
        modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        return modelAndView;


    }
    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name = "id")String id, ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else {


            DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(id);
            if (documentServiceModel == null){
                throw new IllegalArgumentException("Document not found!");
            }
            modelAndView.setViewName("details");
            modelAndView.addObject("model", this.modelMapper.map(documentServiceModel, DocumentServiceModel.class));
        }


    return modelAndView;

    }
    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable("id")String id,ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        } else {
            DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(id);
            if (documentServiceModel == null){
                throw new IllegalArgumentException("Document not found!");
            }
            modelAndView.setViewName("print");
            modelAndView.addObject("model", this.modelMapper.map(documentServiceModel, DocumentServiceModel.class));

        }
        return modelAndView;
    }
    @PostMapping("/print/{id}")
    public ModelAndView printConfirm(@PathVariable("id") String id, ModelAndView modelAndView){

        if (!this.documentService.printDocumentById(id)){
            throw new IllegalArgumentException("Something went wrong");
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

}
