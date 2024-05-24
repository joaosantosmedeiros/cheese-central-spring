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

class DeleteAccountUseCaseTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    @Autowired
    private DeleteAccountUseCase deleteAccountUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should delete an account successfully")
    void deleteAccountSuccess() {
        Account account = new Account("name", "email", "password", false, Role.USER, List.of());
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> {
            deleteAccountUseCase.execute(new DeleteAccountUseCase.Input(account.getEmail()));
        });
    }

    @Test
    @DisplayName("It should throw if no account is found")
    void deleteAccountNotFound() {
        String email = "invalid_email";
        when(accountGateway.findByEmail(email)).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            deleteAccountUseCase.execute(new DeleteAccountUseCase.Input(email));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if the account is already deleted")
    void deleteAccountDeleted() {
        Account account = new Account("name", "email", "password", true, Role.USER, List.of());
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            deleteAccountUseCase.execute(new DeleteAccountUseCase.Input(account.getEmail()));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }
}