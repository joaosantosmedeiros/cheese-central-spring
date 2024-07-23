package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.usecase.account.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AccountConfig {

    @Bean
    public FindAccountByEmailUseCase findAccountByEmailUseCase(AccountGateway accountGateway) {
        return new FindAccountByEmailUseCase(accountGateway);
    }

    @Bean
    public ListAccountsUseCase listAccountsUseCase(AccountGateway accountGateway) {
        return new ListAccountsUseCase(accountGateway);
    }

    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountGateway accountGateway) {
        return new CreateAccountUseCase(accountGateway, new BCryptPasswordEncoder());
    }

    @Bean
    public UpdateAccountUseCase updateAccountUseCase(AccountGateway accountGateway) {
        return new UpdateAccountUseCase(accountGateway, new BCryptPasswordEncoder());
    }


    @Bean
    public DeleteAccountUseCase deleteAccountUseCase(AccountGateway accountGateway) {
        return new DeleteAccountUseCase(accountGateway);
    }
}
