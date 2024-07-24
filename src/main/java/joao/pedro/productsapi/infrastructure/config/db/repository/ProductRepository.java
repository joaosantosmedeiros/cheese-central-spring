package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.db.schema.CategoryEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
}
