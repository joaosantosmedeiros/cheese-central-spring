package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.UUID;

public class CreateProductUseCase {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public CreateProductUseCase(ProductGateway productGateway, CategoryGateway categoryGateway){
        this.productGateway = productGateway;
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        var categoryExists = categoryGateway.findById(input.categoryId);
        if(categoryExists.isEmpty()){
            throw new EntityNotFoundException("Category");
        }
        var category = new Category(
                categoryExists.get().getId(),
                categoryExists.get().getName()
        );

        var product = new Product(
                input.name,
                input.description,
                input.imageUrl,
                input.price,
                category
        );

        this.productGateway.create(product);

        return new Output(product);
    }

    public record Input(
            String name,
            String description,
            String imageUrl,
            Double price,
            UUID categoryId
    ){}

    public record Output(
            Product data
    ){}
}
