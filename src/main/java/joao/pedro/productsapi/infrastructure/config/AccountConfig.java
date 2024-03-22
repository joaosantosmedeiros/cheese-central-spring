package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.usecase.account.CreateAccountUseCase;
import joao.pedro.productsapi.usecase.account.ListAccountsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AccountConfig {

    @Bean
    public ListAccountsUseCase listAccountsUseCase(AccountGateway accountGateway) {
        return new ListAccountsUseCase(accountGateway);
    }

    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountGateway accountGateway) {
        return new CreateAccountUseCase(accountGateway, new BCryptPasswordEncoder());
    }
}
