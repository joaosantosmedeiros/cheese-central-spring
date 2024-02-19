package joao.pedro.productsapi.infrastructure.config.db.repository;

import jakarta.persistence.EntityManager;
import joao.pedro.productsapi.infrastructure.config.db.schema.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Category successfully from DB")
    void findByNameSuccess() {
        String name = "Categoria 1";
        this.createCategory(name);

        Optional<CategoryEntity> result = this.categoryRepository.findByName(name);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Category successfully from DB")
    void findByNameError()  {
        String name = "Categoria 1";

        Optional<CategoryEntity> result = this.categoryRepository.findByName(name);

        assertThat(result.isEmpty()).isTrue();
    }

    private CategoryEntity createCategory (String name) {
        CategoryEntity newCategory = new CategoryEntity(name);
        this.entityManager.persist(newCategory);
        return newCategory;
    }
}