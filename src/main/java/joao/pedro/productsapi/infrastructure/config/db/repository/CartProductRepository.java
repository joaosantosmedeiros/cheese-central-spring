package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.infrastructure.config.db.schema.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProductEntity, UUID> {

    public Optional<CartProductEntity> findByCartIdAndProductId(UUID cartId, UUID productId);
}
