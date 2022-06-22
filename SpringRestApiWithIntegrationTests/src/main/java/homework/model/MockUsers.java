package homework.model;

import homework.enums.Gender;
import homework.enums.Role;
import homework.enums.Status;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

public class MockUsers {
    public static final User[] MOCK_USERS = {
            new User(1L,"Ivan","ivan", "Ivan123#", Gender.MALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
            new User(2L,"Georgi","georgi", "Georgi123#", Gender.MALE, Role.USER, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
            new User(3L,"Alex","alex", "Alex123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
            new User(4L,"Penka","penka", "Penka123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
            new User(5L,"Bangeev","bangeev", "Bangeev123#", Gender.MALE, Role.USER, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Mars_symbol.svg/1024px-Mars_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
            new User(6L,"Plamen","plamen", "Plamen123#", Gender.FEMALE, Role.ADMIN, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Venus_symbol.svg/1024px-Venus_symbol.svg.png",
                    "description", Status.ACTIVE, LocalDateTime.now(),LocalDateTime.now()),
    };
}
