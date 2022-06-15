package homework.service;

import homework.model.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService {

    void loadData();
    Recipe createRecipe(Recipe recipe);
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    Recipe updateRecipe(Recipe recipe);
    Recipe deleteRecipe(Long id);
    List<Recipe> getAllRecipesByTags(Set<String> keywords);
    List<Recipe> getAllRecipesByName(String name);
}
