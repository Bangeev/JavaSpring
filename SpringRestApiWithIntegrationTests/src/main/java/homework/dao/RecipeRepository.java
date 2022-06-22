package homework.dao;

import homework.model.Recipe;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
   List<Recipe> findAllByTagsIn(Iterable<String> tags);
   List<Recipe> findAllByNameContains(String name);
}
