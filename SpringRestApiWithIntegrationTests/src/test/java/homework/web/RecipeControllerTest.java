package homework.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.enums.Gender;
import homework.enums.Role;
import homework.enums.Status;
import homework.model.Recipe;
import homework.model.User;
import homework.service.RecipeService;
import homework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static homework.web.UserControllerTest.MOCK_RECIPE;
import static homework.web.UserControllerTest.MOCK_RECIPES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @MockBean
    private RecipeService mockRecipeService;

    @Test
    void testGetAllRecipes() throws Exception {
        when(mockRecipeService.getAllRecipes()).thenReturn(MOCK_RECIPES);

        var response = mockMvc.perform(get(
                "/api/recipes").accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(MOCK_RECIPES.size()))
                .andExpect(jsonPath("$[*].name",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getName).collect(Collectors.toList()).toArray())))
                .andExpect(jsonPath("$[*].shortDescription",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getShortDescription).collect(Collectors.toList()).toArray()))
                );

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<Recipe>> recipesList = new TypeReference<List<Recipe>>() {
        };

        var recipeList = mapper.readValue(body, recipesList);

        org.hamcrest.MatcherAssert.assertThat(recipeList,
                Matchers.hasItems(MOCK_RECIPES.stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockRecipeService).should(times(1)).getAllRecipes();
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testGetAllRecipesWithSpecificTags() throws Exception {
        when(mockRecipeService.getAllRecipesByTags(Set.of("intro"))).thenReturn(MOCK_RECIPES);

        var response = mockMvc.perform(get(
                "/api/recipes?tags=intro").accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(MOCK_RECIPES.size()))
                .andExpect(jsonPath("$[*].name",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getName).collect(Collectors.toList()).toArray())))
                .andExpect(jsonPath("$[*].shortDescription",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getShortDescription).collect(Collectors.toList()).toArray()))
                );

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<Recipe>> recipesList = new TypeReference<List<Recipe>>() {
        };

        var recipeList = mapper.readValue(body, recipesList);

        org.hamcrest.MatcherAssert.assertThat(recipeList,
                Matchers.hasItems(MOCK_RECIPES.stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockRecipeService).should(times(1)).getAllRecipesByTags(Set.of("intro"));
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testGetAllRecipesWithSpecificName() throws Exception {
        when(mockRecipeService.getAllRecipesByName("one")).thenReturn(MOCK_RECIPES);

        var response = mockMvc.perform(get(
                "/api/recipes?name=one").accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(MOCK_RECIPES.size()))
                .andExpect(jsonPath("$[*].name",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getName).collect(Collectors.toList()).toArray())))
                .andExpect(jsonPath("$[*].shortDescription",
                        Matchers.hasItems(MOCK_RECIPES.stream().map(Recipe::getShortDescription).collect(Collectors.toList()).toArray()))
                );

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<Recipe>> recipesList = new TypeReference<List<Recipe>>() {
        };

        var recipeList = mapper.readValue(body, recipesList);

        org.hamcrest.MatcherAssert.assertThat(recipeList,
                Matchers.hasItems(MOCK_RECIPES.stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockRecipeService).should(times(1)).getAllRecipesByName("one");
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testGetAllRecipes_RecipeServiceGetAllRecipesReturnsNoItems() throws Exception {


        when(mockRecipeService.getAllRecipes()).thenReturn(Collections.emptyList());

        var response = mockMvc.perform(get(
                "/api/recipes").accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(Collections.emptyList().size())
                );

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<Recipe>> recipesList = new TypeReference<List<Recipe>>() {
        };

        var recipeList = mapper.readValue(body, recipesList);

        org.hamcrest.MatcherAssert.assertThat(recipeList,
                Matchers.hasItems(Collections.emptyList().stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockRecipeService).should(times(1)).getAllRecipes();
        then(mockRecipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testCreateRecipe() throws Exception {

        when(mockRecipeService.createRecipe(any(Recipe.class))).thenReturn(MOCK_RECIPE);
        var response = mockMvc.perform(
                post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(MOCK_RECIPE))
                        .accept(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated())
                .andExpect(header().string("location", Matchers.endsWith("/api/users/1/recipes/1")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.name").value(MOCK_RECIPE.getName()));

        var body = response.andReturn().getResponse().getContentAsString();
        var recipe = mapper.readValue(body, Recipe.class);
        org.hamcrest.MatcherAssert.assertThat(recipe,
                Matchers.samePropertyValuesAs(MOCK_RECIPE, "created", "modified"));

        then(mockRecipeService).should(times(1)).createRecipe(MOCK_RECIPE);
        then(mockRecipeService).shouldHaveNoMoreInteractions();
    }
    @Test
    void testGetRecipe() throws Exception {


        when(mockRecipeService.getRecipeById(1L)).thenReturn(MOCK_RECIPE);

        var response = mockMvc.perform(get("/api/recipes/{recipeId}", MOCK_RECIPE.getId())
                .accept(MediaType.APPLICATION_JSON));
            response.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(result -> log.info("HTTPRESPONSE: {}",
                            result.getResponse().getContentAsString()));
            var body = response.andReturn().getResponse().getContentAsString();
            var recipe = mapper.readValue(body ,Recipe.class);

            org.hamcrest.MatcherAssert.assertThat(recipe,
                    Matchers.samePropertyValuesAs(MOCK_RECIPE, "created", "modified"));
            then(mockRecipeService).should(times(1)).getRecipeById(MOCK_RECIPE.getId());
            then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testUpdateRecipe() throws Exception {

        when(mockRecipeService.updateRecipe(any(Recipe.class))).thenReturn(MOCK_RECIPE);

        var response = mockMvc.perform(
                put("/api/recipes/{recipeId}", MOCK_RECIPE.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(MOCK_RECIPE))
                        .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP Resposne: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.id").value(MOCK_RECIPE.getId()))
                .andExpect(jsonPath("$.name").value(MOCK_RECIPE.getName()));


        var body = response.andReturn().getResponse().getContentAsString();
        var recipe = mapper.readValue(body, Recipe.class);

        org.hamcrest.MatcherAssert.assertThat(recipe,
                Matchers.samePropertyValuesAs(MOCK_RECIPE, "created", "modified"));

        then(mockRecipeService).should(times(1)).updateRecipe(MOCK_RECIPE);
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void testDeleteRecipe() throws Exception {

        when(mockRecipeService.deleteRecipe(1L)).thenReturn(MOCK_RECIPE);

        var response = mockMvc.perform(
                delete("/api/recipes/{recipeId}", MOCK_RECIPE.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP Resposne: {}", result.getResponse().getContentAsString()))
        ;


        then(mockRecipeService).should(times(1)).deleteRecipe(MOCK_RECIPE.getId());
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }
}