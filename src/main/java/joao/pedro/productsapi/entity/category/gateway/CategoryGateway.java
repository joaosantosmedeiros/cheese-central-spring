package joao.pedro.productsapi.entity.category.gateway;

import joao.pedro.productsapi.entity.category.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryGateway {
    List<Category> list();
    Optional<Category> findById(UUID id);
    Optional<Category> findByName(String name);

    Category create(Category category);

    Category update(Category category);
}
