package homework.web;

import homework.exception.InvalidDataException;
import homework.exception.InvalidEntityDataException;
import homework.model.Recipe;
import homework.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static homework.util.ErrorsUtil.getAllErrors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

     private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes(@RequestParam(name = "tags", required = false) String tag,
                                      @RequestParam(name = "name", required = false) String name) {
        if (tag!= null && !tag.trim().isEmpty()) {
            Set<String> tags = Set.of(tag.trim().split(",\\s*"));
            return recipeService.getAllRecipesByTags(tags);

        } if (name!= null && !name.trim().isEmpty()) {

            return recipeService.getAllRecipesByName(name);
        }
           else return recipeService.getAllRecipes();
        }





    @PostMapping
    ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }
        Recipe createRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromUriString("/api/users/{userId}/recipes/{recipeId}")
                        .buildAndExpand(createRecipe.getUserId(),createRecipe.getId())
                        .toUri())
                .body(createRecipe);
    }
    @GetMapping("/{id}")
    Recipe getRecipe(@PathVariable Long id){
        return recipeService.getRecipeById(id);
    }

    @PutMapping("/{id}")
    Recipe updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }
        if (!id.equals(recipe.getId())) {
            throw new InvalidEntityDataException(String.format(
                    "Wrong recipe data. id = '%s' of the url is differs from id = '%s' of the recipe body.", id, recipe.getId()));
        }
        return recipeService.updateRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    Recipe deleteRecipe(@PathVariable Long id){
        return recipeService.deleteRecipe(id);
    }


}
