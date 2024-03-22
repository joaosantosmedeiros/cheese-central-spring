package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;

import java.util.List;

public class ListAccountsUseCase {
    private final AccountGateway accountGateway;

    public ListAccountsUseCase(AccountGateway accountGateway){
        this.accountGateway = accountGateway;
    }

    public Output execute() {
        return new Output(accountGateway.list());
    }

    public record Output(
            List<Account> data
    ) {}
}
