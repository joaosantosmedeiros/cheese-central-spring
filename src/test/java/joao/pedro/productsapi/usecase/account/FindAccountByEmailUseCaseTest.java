package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindAccountByEmailUseCaseTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    @Autowired
    private FindAccountByEmailUseCase findAccountByEmailUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should return an account correctly")
    void findAccountByEmailSuccess() {
        Account account = new Account("name", "email", "password", false, Role.USER, List.of());
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        var output =  findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(account.getEmail()));
        assertEquals(account, output.data());
    }

    @Test
    @DisplayName("It should throw if no account is found")
    void findAccountByEmailError() {
        String email = "invalid_email";
        when(accountGateway.findByEmail(email)).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(email));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if a found account is deleted")
    void findAccountByEmailDeletedError() {
        Account account = new Account("name", "email", "password", true, Role.USER, List.of());
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(account.getEmail()));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }
}