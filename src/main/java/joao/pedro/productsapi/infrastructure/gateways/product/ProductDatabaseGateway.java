package joao.pedro.productsapi.infrastructure.gateways.product;

import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.db.repository.ProductRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.CategoryEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductDatabaseGateway implements ProductGateway {

    private final ProductRepository productRepository;

    @Override
    public List<Product> list() {
        return productRepository.findAll().stream().map(productEntity -> {
            var category = new Category(
                    productEntity.getCategory().getId(),
                    productEntity.getCategory().getName()
            );

            return new Product(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getDecription(),
                    productEntity.getImageUrl(),
                    productEntity.getPrice(),
                    category
            );
        }).toList();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category.toCategoryEntity()).stream().map(ProductEntity::toProduct).toList();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id).map(productEntity -> new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getDecription(),
                productEntity.getPrice(),
                new Category(
                        productEntity.getCategory().getId(),
                        productEntity.getCategory().getName()
                )
        ));
    }

    @Override
    public Product create(Product product) {

        var categoryEntity = new CategoryEntity(
                product.getCategory().getId(),
                product.getCategory().getName()
        );

        var productEntity = new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                categoryEntity
        );
        productRepository.save(productEntity);

        return product;
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                new CategoryEntity(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        ));
    }
}
