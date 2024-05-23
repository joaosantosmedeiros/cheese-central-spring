package joao.pedro.productsapi.entity.account.gateway;

import joao.pedro.productsapi.entity.account.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountGateway {
    Account create(Account account);
    List<Account> list();
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    Optional<Account> findById(UUID id);
    void delete(Account account);
}
