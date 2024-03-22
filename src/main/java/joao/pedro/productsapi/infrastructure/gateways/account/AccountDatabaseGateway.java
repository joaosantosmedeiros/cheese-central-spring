package joao.pedro.productsapi.infrastructure.gateways.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.repository.AccountRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
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
                account.isDeleted(),
                account.getRole()
        );
        this.accountRepository.save(accountEntity);

        return account;
    }

    @Override
    public List<Account> list() {
        return accountRepository.findAll().stream().map(accountEntity -> {
            return new Account(
                    accountEntity.getId(),
                    accountEntity.getUsername(),
                    accountEntity.getEmail(),
                    accountEntity.getPassword(),
                    accountEntity.isDeleted(),
                    accountEntity.getRole()
            );
        }).toList();
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username).map(accountEntity -> new Account(
                accountEntity.getId(),
                accountEntity.getUsername(),
                accountEntity.getEmail(),
                accountEntity.getPassword(),
                accountEntity.isDeleted(),
                accountEntity.getRole()
        ));

    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email).map(accountEntity -> new Account(
                accountEntity.getId(),
                accountEntity.getUsername(),
                accountEntity.getEmail(),
                accountEntity.getPassword(),
                accountEntity.isDeleted(),
                accountEntity.getRole()
        ));
    }
}
