package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.UUID;

public class UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        var categoryExists = categoryGateway.findById(input.id());
        if(categoryExists.isEmpty()){
            throw new EntityNotFoundException("Category");
        }

        var categoryAlreadyInUse = categoryGateway.findByName(input.name());
        if(categoryAlreadyInUse.isPresent() && !categoryAlreadyInUse.get().getId().equals(input.id())){
            throw new EntityAlreadyExistsException("Category");
        }

        var updatedCategory = categoryGateway.update(new Category(input.id(), input.name()));

        return new Output(updatedCategory);
    }

    public record Input(
            UUID id,
            String name
    ){}

    public record Output(Category data){}
}
