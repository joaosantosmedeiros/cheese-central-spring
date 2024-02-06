package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.List;

public class ListProductUseCase {

    private final ProductGateway productGateway;

    public ListProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Output execute() {
        return new Output(productGateway.list());
    }

    public record Output(
            List<Product> data
    ) {}
}

