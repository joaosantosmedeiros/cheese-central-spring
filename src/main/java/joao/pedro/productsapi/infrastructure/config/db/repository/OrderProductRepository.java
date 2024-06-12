package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.infrastructure.config.db.schema.OrderEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
    List<OrderProductEntity> findByOrder(OrderEntity order);
}
