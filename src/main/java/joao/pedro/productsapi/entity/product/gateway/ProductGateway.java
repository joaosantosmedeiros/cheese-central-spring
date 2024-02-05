package joao.pedro.productsapi.entity.product.gateway;

import joao.pedro.productsapi.entity.product.model.Product;

public interface ProductGateway {
    Product create(Product product);
}
