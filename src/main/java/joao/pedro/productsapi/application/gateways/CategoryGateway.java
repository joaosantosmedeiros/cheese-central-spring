package joao.pedro.productsapi.application.gateways;

import joao.pedro.productsapi.domain.entity.Category;

import java.util.List;

public interface CategoryGateway {
    List<Category> listCategories();

    Category findCategory(String name);

    Category createCategory(Category category);
}
