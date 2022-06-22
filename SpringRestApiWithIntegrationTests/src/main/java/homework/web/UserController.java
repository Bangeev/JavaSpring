package homework.web;

import homework.exception.InvalidDataException;
import homework.exception.InvalidEntityDataException;
import homework.model.Recipe;
import homework.model.User;
import homework.service.RecipeService;
import homework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static homework.util.ErrorsUtil.getAllErrors;

@RestController
@Slf4j
@RequestMapping(value = "api/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RecipeService recipeService;

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseBody
    ResponseEntity<User> createUser(@Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }

        User created = userService.createUser(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .pathSegment("{id}")
                        .buildAndExpand(created.getId())
                        .toUri())
                .body(created);
    }
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    User updateUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidDataException("Invalid user data", getAllErrors(errors));
        }
        if (!id.equals(user.getId())) {
            throw new InvalidEntityDataException(String.format(
                    "Wrong user data. id = '%s' of the url is differs from id = '%s' of the user body.", id, user.getId()));
        }
        return userService.updateUser(user);
    }


    @DeleteMapping("/{id}")
    User deleteUser(@PathVariable Long id){
        return userService.deleteUserById(id);
    }

    @GetMapping("/{id}/recipes/{recipeId}")
    Recipe getRecipeByIdForUser(@PathVariable Long id, @PathVariable Long recipeId) {
        userService.getUserById(id);
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (!recipe.getUserId().equals(id)) {
            throw new InvalidEntityDataException(
                    String.format("Recipe for user with id = %s not found.", id));
        }
        return recipe;
    }

    @GetMapping("/{id}/recipes")
    List<Recipe> getAllRecipeByIdForUser(@PathVariable Long id) {
        userService.getUserById(id);
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        var forUser = allRecipes.stream().filter(recipe -> recipe.getUserId().equals(id)).collect(Collectors.toList());
        if (forUser.isEmpty()) {
            throw new InvalidEntityDataException(
                    String.format("Recipe for user with id = %s not found.", id));
        }
        return forUser;
    }

}
