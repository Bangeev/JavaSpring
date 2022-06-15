package homework.model;

import homework.enums.Gender;
import homework.enums.Role;
import homework.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(name = "UC_username", columnNames = {"username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    @Size(max = 15)
    @Basic(optional = false)
    private String username;
    @NonNull
    @NotNull
    @Pattern(regexp = "^.*(?=.{8,15})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&?+]).*$",
            message = "Password should contain at least 8 characters, at least one digit, capital letter, and none-letter character")
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
    private String url;
    @Size(max = 512)
    private String shortDescription;
    @NotNull
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();




}
