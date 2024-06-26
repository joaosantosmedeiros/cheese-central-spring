package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.infrastructure.config.db.schema.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
