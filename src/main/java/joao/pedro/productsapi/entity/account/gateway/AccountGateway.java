package joao.pedro.productsapi.entity.account.gateway;

import joao.pedro.productsapi.entity.account.model.Account;

public interface AccountGateway {
    Account create(Account account);
}
