package joao.pedro.productsapi.infrastructure.persistence.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    CategoryEntity findByName(String name);
}
