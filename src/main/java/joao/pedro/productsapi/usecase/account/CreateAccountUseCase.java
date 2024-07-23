package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.ObjectInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

public class CreateAccountUseCase {

    private final AccountGateway accountGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateAccountUseCase(AccountGateway accountGateway, PasswordEncoder passwordEncoder) {
        this.accountGateway = accountGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Output execute(Input input) {

        var emailIsInUse = accountGateway.findByEmail(input.email);
        if(emailIsInUse.isPresent()) {
            throw new ObjectInUseException("Email");
        }

        var usernameIsInUse = accountGateway.findByUsername(input.username);
        if(usernameIsInUse.isPresent()) {
            throw new ObjectInUseException("Username");
        }

        String encodedPassword = passwordEncoder.encode(input.password());

        Account account = new Account(
                input.username,
                input.email,
                encodedPassword,
                false,
                Role.USER,
                Arrays.asList()
        );

        accountGateway.create(account);

        return new Output(new FetchedAccount(account));
    }

    public record Input(
            String username,
            String email,
            String password
    ) {}

    public record Output ( FetchedAccount data ) {}
}
