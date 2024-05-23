package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.UUID;

public class FindAccountByIdUseCase {

    private final AccountGateway accountGateway;

    public FindAccountByIdUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Output execute(Input input) {
        Account account = accountGateway.findById(input.id).orElseThrow(() -> new EntityNotFoundException("Account"));

        if(account.isDeleted()){
            throw new EntityNotFoundException("Account");
        }

        return new Output(account);
    }

    public record Input(UUID id) {}
    public record Output(Account data) {}
}
