package homework.model;

import homework.enums.Gender;
import homework.enums.Role;
import homework.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MockRecipes {
    static List<String> products = List.of("Test","test","Test");

    public static final Recipe[] MOCK_RECIPES = {
            new Recipe(1L,1L,"Recipe one","Short Description", 60 , products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "intro"), LocalDateTime.now(), LocalDateTime.now() ),
            new Recipe(2L,2L,"Recipe two","Short Description", 60 ,products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "python"), LocalDateTime.now(), LocalDateTime.now() ),
            new Recipe(3L,3L,"Recipe three","Short Description", 60 ,products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("python"), LocalDateTime.now(), LocalDateTime.now() ),
            new Recipe(4L,4L,"Recipe four","Short Description", 60 ,products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("csharp", "intro"), LocalDateTime.now(), LocalDateTime.now() ),
            new Recipe(5L,5L,"Recipe five","Short Description", 60 ,products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("html", "intro"), LocalDateTime.now(), LocalDateTime.now() ),
            new Recipe(6L,6L,"Recipe six","Short Description", 60 ,products, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1280px-Good_Food_Display_-_NCI_Visuals_Online.jpg", "Long description", Set.of("java", "code"), LocalDateTime.now(), LocalDateTime.now() ),

    };

}
