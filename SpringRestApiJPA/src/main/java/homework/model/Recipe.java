package homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotNull
    private Long userId;
    @NonNull
    @NotNull
    @Size(max = 80)
    private String name;
    @NonNull
    @NotNull
    @Size(max = 256)
    private String shortDescription;
    @NonNull
    @NotNull
    private Integer cookingTimeMinutes;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> products;
    @NonNull
    @NotNull
    private String image;
    @NonNull
    @NotNull
    @Size(max = 2048)
    private String longDescription;
    //@ElementCollection(fetch = FetchType.EAGER)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<String> tags;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();




}
