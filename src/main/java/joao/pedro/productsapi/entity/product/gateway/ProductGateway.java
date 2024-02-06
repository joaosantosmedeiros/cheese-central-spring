package joao.pedro.productsapi.entity.product.gateway;

import joao.pedro.productsapi.entity.product.model.Product;

import java.util.List;

public interface ProductGateway {
    List<Product> list();

    Product create(Product product);
}
