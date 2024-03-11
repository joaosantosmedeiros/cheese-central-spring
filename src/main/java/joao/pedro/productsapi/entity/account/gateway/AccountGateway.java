package joao.pedro.productsapi.entity.account.gateway;

import joao.pedro.productsapi.entity.account.model.Account;

import java.util.Optional;

public interface AccountGateway {
    Account create(Account account);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
}
