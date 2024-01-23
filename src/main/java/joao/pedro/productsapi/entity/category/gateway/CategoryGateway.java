package joao.pedro.productsapi.entity.category.gateway;

import joao.pedro.productsapi.entity.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    List<Category> list();

    Optional<Category> find(String name);

    Category create(Category category);
}
