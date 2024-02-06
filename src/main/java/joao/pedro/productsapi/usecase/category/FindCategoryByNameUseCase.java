package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.Optional;

public class FindCategoryByNameUseCase {
    private final CategoryGateway categoryGateway;

    public FindCategoryByNameUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        Optional<Category> data = this.categoryGateway.findByName(input.name());
        if(data.isEmpty()){
            throw new EntityNotFoundException("Category");
        }
        return new Output(data.get());
    }

    public record Input(String name) {}
    public record Output(Category data) {}
}
