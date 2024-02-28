package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.UUID;

public class DeleteProductUseCase {
    private final ProductGateway productGateway;

    public DeleteProductUseCase (ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public void execute(Input input) {
        var product = productGateway.findById(input.id).orElseThrow(() -> new EntityNotFoundException("Product"));
        productGateway.delete(product);
    }

    public record Input(UUID id) {}
}
