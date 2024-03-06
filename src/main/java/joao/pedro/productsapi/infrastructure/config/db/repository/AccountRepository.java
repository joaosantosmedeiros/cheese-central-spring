package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}
