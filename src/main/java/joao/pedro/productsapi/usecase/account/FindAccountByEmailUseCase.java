package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.Optional;

public class FindAccountByEmailUseCase {

    private final AccountGateway accountGateway;

    public FindAccountByEmailUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Output execute(Input input) {
        Account account = accountGateway.findByEmail(input.email).orElseThrow(() -> new EntityNotFoundException("Account"));

        if(account.isDeleted()){
            throw new EntityNotFoundException("Account");
        }

        return new Output(account);
    }

    public record Input(String email) {}
    public record Output(Account data) {}
}
