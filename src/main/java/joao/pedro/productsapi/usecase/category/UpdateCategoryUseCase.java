package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;

import java.util.UUID;

public class UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        var categoryExists = categoryGateway.findById(input.id());
        if(categoryExists.isEmpty()){
            return new Output(false, "Category does not exists.", null);
        }

        var categoryAlreadyInUse = categoryGateway.findByName(input.name());
        if(categoryAlreadyInUse.isPresent() && !categoryAlreadyInUse.get().getId().equals(input.id())){
            return new Output(false, "Category name already in use.", null);
        }

        var updatedCategory = categoryGateway.update(new Category(input.id(), input.name()));

        return new Output(
                true,
                "Category updated sucessfully",
                updatedCategory
        );
    }

    public record Input(
            UUID id,
            String name
    ){}

    public record Output(
            Boolean status,
            String message,
            Category data
    ){}
}
