package joao.pedro.productsapi.application.usecases.category;

import joao.pedro.productsapi.application.gateways.CategoryGateway;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindCategoryInteractor {
    private CategoryGateway categoryGateway;

    public Category findCategory(String name) {
        return categoryGateway.findCategory(name);
    }
}
