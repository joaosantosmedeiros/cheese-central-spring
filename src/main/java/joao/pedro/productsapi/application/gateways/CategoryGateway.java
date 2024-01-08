package joao.pedro.productsapi.application.gateways;

import joao.pedro.productsapi.domain.entity.Category;

public interface CategoryGateway {
    Category findCategory(String name);

    Category createCategory(Category category);
}
