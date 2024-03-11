package joao.pedro.productsapi.infrastructure.config.db.repository;

import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUsername(String username);
    Optional<AccountEntity> findByEmail(String email);
}
