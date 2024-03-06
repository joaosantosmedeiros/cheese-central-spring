package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.UUID;

public class UpdateProductUseCase {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public UpdateProductUseCase(ProductGateway productGateway, CategoryGateway categoryGateway) {
        this.productGateway = productGateway;
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        productGateway.findById(input.id).orElseThrow(() -> new EntityNotFoundException("Product"));
        var categoryExists = categoryGateway.findById(input.categoryId).orElseThrow(() -> new EntityNotFoundException("Category"));

        Product product = new Product(
                input.id,
                input.name,
                input.description,
                input.imageUrl,
                input.price,
                new Category(
                        input.categoryId,
                        categoryExists.getName()
                )
        );

        productGateway.create(product);

        return new Output(product);
    }

    public record Input(
      UUID id,
      String name,
      String description,
      String imageUrl,
      Double price,
      UUID categoryId
    ) {}

    public record Output(Product data) {}
}
