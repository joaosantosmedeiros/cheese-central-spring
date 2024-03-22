package joao.pedro.productsapi.usecase.account;

import joao.pedro.productsapi.entity.account.gateway.AccountGateway;
import joao.pedro.productsapi.entity.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListAccountsUseCaseTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    @Autowired
    private ListAccountsUseCase listAccountsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should list accounts successfully")
    void listAccountUseCaseTest() {
        when(accountGateway.list()).thenReturn(new ArrayList<Account>());

        var output = listAccountsUseCase.execute();

        assertNotNull(output.data());
    }
}