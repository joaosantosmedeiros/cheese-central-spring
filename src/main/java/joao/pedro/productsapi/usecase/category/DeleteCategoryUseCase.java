package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.ObjectInUseException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;

import java.util.UUID;

public class DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;
    private final ProductGateway productGateway;

    public DeleteCategoryUseCase(CategoryGateway categoryGateway, ProductGateway productGateway) {
        this.categoryGateway = categoryGateway;
        this.productGateway = productGateway;
    }

    public void execute(Input input) {

        var categoryExists = this.categoryGateway.findById(input.id()).orElseThrow(() -> new EntityNotFoundException("Category"));

        if(!productGateway.findByCategory(categoryExists.toCategory()).isEmpty()){
            throw new ObjectInUseException("Category") ;
        }

        this.categoryGateway.delete(categoryExists);
    }

    public record Input(UUID id){}

}
