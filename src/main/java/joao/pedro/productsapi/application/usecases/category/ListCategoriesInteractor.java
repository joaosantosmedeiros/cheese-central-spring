package joao.pedro.productsapi.application.usecases.category;

import joao.pedro.productsapi.application.gateways.CategoryGateway;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListCategoriesInteractor {
    private CategoryGateway categoryGateway;

    public List<Category> listCategories() {
        return categoryGateway.listCategories();
    }
}
