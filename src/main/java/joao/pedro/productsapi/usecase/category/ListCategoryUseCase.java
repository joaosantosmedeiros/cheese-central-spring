package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;

import java.util.List;

public class ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public ListCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute() {
        List<Category> data = this.categoryGateway.list();
        return new Output(data);
    }

    public record Output(List<Category> data) {}
}
