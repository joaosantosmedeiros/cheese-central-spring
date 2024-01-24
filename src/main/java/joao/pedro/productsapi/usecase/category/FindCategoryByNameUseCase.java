package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;

import java.util.Optional;

public class FindCategoryByNameUseCase {
    private final CategoryGateway categoryGateway;

    public FindCategoryByNameUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        Optional<Category> data = this.categoryGateway.findByName(input.name());
        if(data.isEmpty()){
            return new Output(false, "Category not found.", null);
        }
        return new Output(true, "Showing found category.", data.get());
    }

    public record Input(String name) {}
    public record Output(Boolean status, String message, Category data) {}
}
