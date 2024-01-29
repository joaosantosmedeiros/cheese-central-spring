package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.UUID;

public class DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {

        var categoryExists = this.categoryGateway.findById(input.id());
        if(categoryExists.isEmpty()){
            throw new EntityNotFoundException("Category");
        }

        this.categoryGateway.delete(categoryExists.get());

        return new Output(
                true,
                "Category deleted sucessfully."
        );
    }

    public record Input(
            UUID id
    ){}

    public  record Output(
            Boolean status,
            String message
    ){}
}
