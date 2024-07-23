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
        var categoryExists = categoryGateway.findById(input.category.getId()).orElseThrow(() -> new EntityNotFoundException("Category"));

        var categoryAlreadyInUse = categoryGateway.findByName(input.category.getName());
        if(categoryAlreadyInUse.isPresent() && !categoryAlreadyInUse.get().getId().equals(input.category.getId())){
            throw new EntityAlreadyExistsException("Category");
        }

        categoryExists.setName(input.category.getName());
        return new Output(categoryGateway.update(categoryExists));
    }

    public record Input(Category category){}

    public record Output(Category data){}
}
