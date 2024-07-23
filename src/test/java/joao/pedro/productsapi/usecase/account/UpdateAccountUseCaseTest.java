package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.ObjectInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateAccountUseCaseTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountGateway accountGateway;

    @Autowired
    @InjectMocks
    private UpdateAccountUseCase updateAccountUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should update an account successfully")
    public void updateAccountSuccess() {
        var account = new Account("name", "email", "password", false, Role.USER, List.of());
        var expectedAccount = new Account("updatedName", account.getEmail(), "updatedPassword", false, Role.USER, List.of());
        var input  = new UpdateAccountUseCase.Input(
                new Account("updatedName", account.getEmail(), "updatedPassword", false, Role.USER, List.of())
        );
        when(passwordEncoder.encode(any())).thenReturn("updatedPassword");
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        when(accountGateway.findByUsername("updatedName")).thenReturn(Optional.empty());
        when(accountGateway.update(any())).thenReturn(expectedAccount);

        var updatedAccount = updateAccountUseCase.execute(input).data();

        assertEquals(new FetchedAccount(expectedAccount), updatedAccount);
    }

    @Test
    @DisplayName("It should throw if no account is found")
    public void updateAccountErrorNotFound() {
        var account = new Account("name", "email", "password", false, Role.USER, List.of());
        var input  = new UpdateAccountUseCase.Input(
                new Account("updatedName", account.getEmail(), "updatedPassword", false, Role.USER, List.of())
        );
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class,() -> {
            updateAccountUseCase.execute(input);
        });

        assertEquals("Account not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if the account username is already in use")
    public void updateAccountErrorUsernameInUse() {
        var account = new Account("name", "email", "password", false, Role.USER, List.of());
        var expectedAccount = new Account("updatedName", account.getEmail(), "updatedPassword", false, Role.USER, List.of());
        var input  = new UpdateAccountUseCase.Input(
                new Account("updatedName", account.getEmail(), "updatedPassword", false, Role.USER, List.of())
        );
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        when(accountGateway.findByUsername("updatedName")).thenReturn(Optional.of(
                new Account("anotherName", "another@mail.com",  "pass", false, Role.USER, List.of()))
        );

        var thrown = assertThrows(ObjectInUseException.class,() -> {
            updateAccountUseCase.execute(input);
        });

        assertEquals("Username already in use.", thrown.getMessage());
    }
}