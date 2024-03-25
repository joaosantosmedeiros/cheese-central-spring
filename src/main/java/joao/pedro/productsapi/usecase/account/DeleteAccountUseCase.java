package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

public class DeleteAccountUseCase {

    private final AccountGateway accountGateway;

    public DeleteAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public void execute(Input input){
        var account = accountGateway.findByEmail(input.email).orElseThrow(() -> new EntityNotFoundException("Account"));
        if(account.isDeleted()){
            throw new EntityNotFoundException("Account");
        }

        accountGateway.delete(account);
    }

    public record Input(String email) {}
}
