package joao.pedro.productsapi.entity.product.gateway;

import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {
    List<Product> list();

    List<Product> findByCategory(Category category);

    Optional<Product> findById(UUID id);

    Product create(Product product);

    void delete(Product product);
}
