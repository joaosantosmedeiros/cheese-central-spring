package joao.pedro.productsapi.infrastructure.gateways.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.repository.AccountRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountDatabaseGateway implements AccountGateway {

    private AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getPassword(),
                account.isAdmin(),
                account.isDeleted()
        );
        this.accountRepository.save(accountEntity);

        return account;
    }
}
