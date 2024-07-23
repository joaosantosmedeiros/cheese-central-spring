package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.ObjectInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UpdateAccountUseCase {

    private final AccountGateway accountGateway;
    private final PasswordEncoder passwordEncoder;

    public UpdateAccountUseCase(AccountGateway accountGateway, PasswordEncoder passwordEncoder) {
        this.accountGateway = accountGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Output execute(Input input) {
        var accountExists = accountGateway.findByEmail(input.account.getEmail()).orElseThrow(() -> new EntityNotFoundException("Account"));
        if(accountExists.isDeleted()) throw new EntityNotFoundException("Account");

        if(input.account.getUsername() != null && !input.account.getUsername().trim().isBlank()){
            var usernameIsInUse = accountGateway.findByUsername(input.account.getUsername());
            if(usernameIsInUse.isPresent() && usernameIsInUse.get().getId() != accountExists.getId()){
                throw new ObjectInUseException("Username");
            }
            accountExists.setUsername(input.account.getUsername());
        }

        if(input.account.getPassword() != null && !input.account.getPassword().trim().isBlank()) {
            accountExists.setPassword(passwordEncoder.encode(input.account.getPassword()));
        }

        return new Output(new FetchedAccount(accountGateway.update(accountExists)));
    }

    public record Input(Account account) {}
    public record Output(FetchedAccount data) {}
}
