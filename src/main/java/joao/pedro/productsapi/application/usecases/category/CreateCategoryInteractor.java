package joao.pedro.productsapi.application.usecases.category;

import joao.pedro.productsapi.application.gateways.CategoryGateway;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCategoryInteractor {
    private CategoryGateway categoryGateway;

    public Category createCategory(Category category) {
        return categoryGateway.createCategory(category);
    }
}
