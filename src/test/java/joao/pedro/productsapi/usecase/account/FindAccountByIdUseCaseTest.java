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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindAccountByIdUseCaseTest {

    @Autowired
    @InjectMocks
    private FindAccountByIdUseCase findAccountByIdUseCase;

    @Mock
    private AccountGateway accountGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should return an account correctly")
    void findAccountByIdSuccess() {
        Account account = new Account("name", "email", "password", false, Role.USER, List.of());
        when(accountGateway.findById(account.getId())).thenReturn(Optional.of(account));

        var output =  findAccountByIdUseCase.execute(new FindAccountByIdUseCase.Input(account.getId()));
        assertEquals(account, output.data());
    }

    @Test
    @DisplayName("It should throw if no account is found")
    void findAccountByIdError() {
        UUID id = UUID.randomUUID();
        when(accountGateway.findById(id)).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            findAccountByIdUseCase.execute(new FindAccountByIdUseCase.Input(id));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if a found account is deleted")
    void findAccountByIdDeletedError() {
        Account account = new Account("name", "email", "password", true, Role.USER, List.of());
        when(accountGateway.findById(account.getId())).thenReturn(Optional.of(account));

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            findAccountByIdUseCase.execute(new FindAccountByIdUseCase.Input(account.getId()));
        });

        assertEquals("Account not found.", thrown.getMessage());
    }
}