package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.enums.Role;
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
import static org.mockito.Mockito.when;

class CreateAccountUseCaseTest {

    @Mock
    private AccountGateway accountGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("It should create an account successfully")
    @Test
    void createAccountSuccess() {
        Account account = new Account(
                "any_name",
                "mail@mail.com",
                "any_pass",
                false,
                Role.USER,
                List.of()
        );

        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.empty());
        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.empty());

        var output = createAccountUseCase.execute(new CreateAccountUseCase.Input(
                account.getUsername(),
                account.getEmail(),
                account.getPassword()
        ));

        assertEquals(output.data().getEmail(), account.getEmail());
        assertEquals(output.data().getUsername(), account.getUsername());
        assertEquals(output.data().getRole(), Role.USER);
        assertFalse(output.data().isDeleted());
    }

    @DisplayName("It should throw if an invalid email is passed")
    @Test
    void createAccountInvalidEmail() {
        Account account = new Account(
                "any_name",
                "mail@mail.com",
                "any_pass",
                false,
                Role.USER,
                List.of()
        );

        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        Exception e = assertThrows(ObjectInUseException.class, () -> {
            createAccountUseCase.execute(new CreateAccountUseCase.Input(
                    account.getUsername(),
                    account.getEmail(),
                    account.getPassword()
            ));
        });

        assertEquals("Email already in use.", e.getMessage());
    }

    @DisplayName("It should throw if an invalid email is passed")
    @Test
    void createAccountInvalidUsername() {
        Account account = new Account(
                "any_name",
                "mail@mail.com",
                "any_pass",
                false,
                Role.USER,
                List.of()
        );

        when(accountGateway.findByEmail(account.getEmail())).thenReturn(Optional.empty());
        when(accountGateway.findByUsername(account.getUsername())).thenReturn(Optional.of(account));

        Exception e = assertThrows(ObjectInUseException.class, () -> {
            createAccountUseCase.execute(new CreateAccountUseCase.Input(
                    account.getUsername(),
                    account.getEmail(),
                    account.getPassword()
            ));
        });

        assertEquals("Username already in use.", e.getMessage());
    }
}