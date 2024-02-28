package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.Optional;
import java.util.UUID;

public class FindProductByIdUseCase {
    private final ProductGateway productGateway;

    public FindProductByIdUseCase (ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Output execute(Input input) {
        Optional<Product> product = productGateway.findById(input.id);
        if(product.isEmpty()) {
            throw new EntityNotFoundException("Product");
        }

        return new Output(product.get());
    }

    public record Input (UUID id) {}

    public record Output(Product data) {}
}
