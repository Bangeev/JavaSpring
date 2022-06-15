package homework.service.impl;

import homework.dao.RecipeRepository;
import homework.exception.InvalidEntityDataException;
import homework.exception.NonexistingEntityException;
import homework.model.Recipe;
import homework.model.User;
import homework.service.RecipeService;
import homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static homework.model.MockRecipes.MOCK_RECIPES;
import static homework.model.MockUsers.MOCK_USERS;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostConstruct
    @Override
    public void loadData() {
        if(recipeRepository.count() == 0) {
            recipeRepository.saveAll(Arrays.asList(MOCK_RECIPES));
        }
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("Recipe with ID='%s' does not exist.", id))
        );
        return recipe;

    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        Recipe old = getRecipeById(recipe.getId());
        if (!old.getName().equals(recipe.getName())) {
            throw new InvalidEntityDataException(
                    String.format("Recipe '%s' can not be changed to '%s'.",
                            old.getName(), recipe.getName()));
        }
        recipe.setCreated(recipe.getCreated());
        recipe.setModified(LocalDateTime.now());

        return recipeRepository.save(recipe);

    }

    @Override
    public Recipe deleteRecipe(Long id) {
        Recipe old = recipeRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("Recipe with ID='%s' does not exist.", id)));
        recipeRepository.deleteById(id);

        return old;
    }

    @Override
    public List<Recipe> getAllRecipesByTags(Set<String> tags) {
        return recipeRepository.findAllByTagsIn(tags);
    }
    public List<Recipe> getAllRecipesByName(String name) {
        return recipeRepository.findAllByNameContains(name);
    }


}
