package homework.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dao.UserRepository;
import homework.enums.Gender;
import homework.enums.Role;
import homework.enums.Status;
import homework.exception.InvalidDataException;
import homework.exception.InvalidEntityDataException;
import homework.exception.NonexistingEntityException;
import homework.model.Recipe;
import homework.model.User;
import homework.service.RecipeService;
import homework.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
class UserControllerTest {


    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserService mockUserService;
    @MockBean
    private RecipeService mockRecipeService;


    @Test
    public void testGetAllUsers() throws Exception {

        when(mockUserService.getAllUsers()).thenReturn(MOCK_USERS);

        var response = mockMvc.perform(get("/api/users").accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(MOCK_USERS.size()))
                .andExpect(jsonPath("$[*].name",
                        Matchers.hasItems(MOCK_USERS.stream().map(User::getName).collect(Collectors.toList()).toArray())))
                .andExpect(jsonPath("$[*].username",
                        Matchers.hasItems(MOCK_USERS.stream().map(User::getUsername).collect(Collectors.toList()).toArray()))
                );

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<User>> usersList = new TypeReference<List<User>>() {
        };

        var userList = mapper.readValue(body, usersList);

        org.hamcrest.MatcherAssert.assertThat(userList,
                Matchers.hasItems(MOCK_USERS.stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockUserService).should(times(1)).getAllUsers();
        then(mockUserService).shouldHaveNoMoreInteractions();
    }


    @Test
    public void testGetAllUsers_UserServiceReturnsNoItems() throws Exception {

        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());


        var resposne = mockMvc.perform(get("/api/users").accept(APPLICATION_JSON));

        resposne.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()));

        var body = resposne.andReturn().getResponse().getContentAsString();
        TypeReference<List<User>> usersList = new TypeReference<List<User>>() {
        };

        var userList = mapper.readValue(body, usersList);
        org.hamcrest.MatcherAssert.assertThat(userList,
                Matchers.hasItems(Collections.emptyList().stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));
        then(mockUserService).should(times(1)).getAllUsers();
        then(mockUserService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testCreateUser() throws Exception {

        when(mockUserService.createUser(any(User.class))).thenReturn(MOCK_CREATED_USER);

        var response = mockMvc.perform(
                post("/api/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(MOCK_USER))
                        .accept(APPLICATION_JSON)
        );

        response.andExpect(status().isCreated())
                .andExpect(header().string("location", Matchers.endsWith("/api/users/1")))
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.username").value(MOCK_CREATED_USER.getUsername()));

        var body = response.andReturn().getResponse().getContentAsString();
        var user = mapper.readValue(body, User.class);
        org.hamcrest.MatcherAssert.assertThat(user,
                Matchers.samePropertyValuesAs(MOCK_CREATED_USER, "created", "modified"));

        then(mockUserService).should(times(1)).createUser(MOCK_USER);
        then(mockUserService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testGetUserById() throws Exception {

        when(mockUserService.getUserById(1L)).thenReturn(MOCK_USER);

        var response = mockMvc.perform(get("/api/users/{userId}", MOCK_USER.getId())
                .accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}",
                        result.getResponse().getContentAsString()));

        var body = response.andReturn().getResponse().getContentAsString();

        var user = mapper.readValue(body, User.class);

        org.hamcrest.MatcherAssert.assertThat(user,
                Matchers.samePropertyValuesAs(MOCK_CREATED_USER, "created", "modified"));

        then(mockUserService).should(times(1)).getUserById(MOCK_USER.getId());
        then(mockUserService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testUpdateUser() throws Exception {

        when(mockUserService.updateUser(any(User.class))).thenReturn(MOCK_USER);

        var response = mockMvc.perform(
                put("/api/users/{userId}", MOCK_USER.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(MOCK_USER))
                        .accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP Resposne: {}", result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.id").value(MOCK_USER.getId()))
                .andExpect(jsonPath("$.role").value(MOCK_USER.getRole().name()));


        var body = response.andReturn().getResponse().getContentAsString();
        var user = mapper.readValue(body, User.class);

        org.hamcrest.MatcherAssert.assertThat(user,
                Matchers.samePropertyValuesAs(MOCK_USER, "created", "modified"));

        then(mockUserService).should(times(1)).updateUser(MOCK_USER);
        then(mockUserService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testDeleteUser() throws Exception {

        when(mockUserService.deleteUserById(1L)).thenReturn(MOCK_USER);

        var response = mockMvc.perform(
                delete("/api/users/{userId}", MOCK_USER.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP Resposne: {}", result.getResponse().getContentAsString()))
        ;


        then(mockUserService).should(times(1)).deleteUserById(MOCK_USER.getId());
        then(mockUserService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testGetRecipeByIdForUser() throws Exception {

        when(mockRecipeService.getRecipeById(1L)).thenReturn(MOCK_RECIPE);


        var response = mockMvc.perform(get(
                "/api/users/{id}/recipes/{recipeId}", MOCK_RECIPE.getUserId(), MOCK_RECIPE.getId())
                .accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}",
                        result.getResponse().getContentAsString()));

        var body = response.andReturn().getResponse().getContentAsString();

        var recipe = mapper.readValue(body, Recipe.class);

        org.hamcrest.MatcherAssert.assertThat(recipe,
                Matchers.samePropertyValuesAs(MOCK_RECIPE, "created", "modified"));

        then(mockRecipeService).should(times(1)).getRecipeById(MOCK_RECIPE.getUserId());
        then(mockRecipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void testGetAllRecipeByIdForUser() throws Exception {

        when(mockUserService.getUserById(1L)).thenReturn(MOCK_USER);

        when(mockRecipeService.getAllRecipes()).thenReturn(MOCK_RECIPES);

        var response = mockMvc.perform(get(
                "/api/users/{id}/recipes", MOCK_USER.getId())
                .accept(APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.info("HTTP RESPONSE: {}",
                        result.getResponse().getContentAsString()));

        var body = response.andReturn().getResponse().getContentAsString();
        TypeReference<List<Recipe>> recipeList = new TypeReference<List<Recipe>>() {
        };

        var userRecipeList = mapper.readValue(body, recipeList);


        org.hamcrest.MatcherAssert.assertThat(userRecipeList,
                Matchers.hasItems(MOCK_RECIPES.stream().map(u -> Matchers.samePropertyValuesAs(u, "created", "modified"))
                        .collect(Collectors.toList()).toArray(new Matcher[]{})));

        then(mockUserService).should(times(1)).getUserById(MOCK_USER.getId());
        then(mockUserService).shouldHaveNoMoreInteractions();
        then(mockRecipeService).should(times(1)).getAllRecipes();
        then(mockRecipeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void whenCreatingUserButIsWithWrongDataThrowException() throws Exception {

        when(mockUserService.createUser(any(User.class))).thenReturn(MOCK_USER);

        var response = mockMvc.perform(
                post("/api/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(MOCK_USER_EMPTY))
                        .accept(APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(header().doesNotExist("location"))
                //.andExpect(content().contentType(APPLICATION_JSON))
                .andDo(result -> log.info("HTTP RESPONSE: {}", result.getResponse().getContentAsString()));
        then(mockUserService).shouldHaveNoMoreInteractions();
    }





    static List<String> PRODUCTS = List.of("Test", "test", "Test");
    public static final List<User> MOCK_USERS = List.of(
            new User(1L, "Ivan", "ivan", "Ivan123#", Gender.MALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now()),
            new User(2L, "Georgi", "georgi", "Georgi123#", Gender.MALE, Role.USER, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now()),
            new User(3L, "Alex", "alex", "Alex123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now()),
            new User(4L, "Penka", "penka", "Penka123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now()),
            new User(5L, "Bangeev", "bangeev", "Bangeev123#", Gender.MALE, Role.USER, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now()),
            new User(6L, "Plamen", "plamen", "Plamen123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now())
    );

    public static final List<Recipe> MOCK_RECIPES = List.of(
            new Recipe(1L, 1L, "Recipe one", "Short Description", 60, PRODUCTS, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "intro"), LocalDateTime.now(), LocalDateTime.now()),
            new Recipe(2L, 1L, "Recipe two", "Short Description", 60, PRODUCTS, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "python"), LocalDateTime.now(), LocalDateTime.now())
    );

    public static final User MOCK_USER_EMPTY = new User();

    public static final User MOCK_USER = new User(1L, "Ivan", "ivan", "Ivan123#", Gender.MALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
            "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now());
    public static final User MOCK_CREATED_USER = new User(1L, "Ivan", "ivan", "Ivan123#", Gender.MALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
            "description", Status.ACTIVE, LocalDateTime.now(), LocalDateTime.now());


    public static final String MOCK_USERS_AS_STRING = "[{\"id\":1,\"name\":\"Ivan\",\"username\":\"ivan\",\"password\":\"Ivan123#\",\"gender\":\"MALE\",\"role\":\"ADMIN\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9338132\",\"modified\":\"2022-05-11T21:51:05.9348115\"},{\"id\":2,\"name\":\"Georgi\",\"username\":\"georgi\",\"password\":\"Georgi123#\",\"gender\":\"MALE\",\"role\":\"USER\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9348115\",\"modified\":\"2022-05-11T21:51:05.9348115\"},{\"id\":3,\"name\":\"Alex\",\"username\":\"alex\",\"password\":\"Alex123#\",\"gender\":\"FEMALE\",\"role\":\"ADMIN\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9348115\",\"modified\":\"2022-05-11T21:51:05.9348115\"},{\"id\":4,\"name\":\"Penka\",\"username\":\"penka\",\"password\":\"Penka123#\",\"gender\":\"FEMALE\",\"role\":\"ADMIN\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9348115\",\"modified\":\"2022-05-11T21:51:05.9348115\"},{\"id\":5,\"name\":\"Bangeev\",\"username\":\"bangeev\",\"password\":\"Bangeev123#\",\"gender\":\"MALE\",\"role\":\"USER\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9348115\",\"modified\":\"2022-05-11T21:51:05.9348115\"},{\"id\":6,\"name\":\"Plamen\",\"username\":\"plamen\",\"password\":\"Plamen123#\",\"gender\":\"FEMALE\",\"role\":\"ADMIN\",\"url\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png\",\"shortDescription\":\"description\",\"status\":\"ACTIVE\",\"created\":\"2022-05-11T21:51:05.9358075\",\"modified\":\"2022-05-11T21:51:05.9358075\"}]";

    public static final Recipe MOCK_RECIPE = new Recipe(1L, 1L, "Recipe one", "Short Description", 60, PRODUCTS, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "intro"), LocalDateTime.now(), LocalDateTime.now());

}

