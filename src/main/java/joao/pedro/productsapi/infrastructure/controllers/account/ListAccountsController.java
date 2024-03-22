package joao.pedro.productsapi.infrastructure.controllers.account;

import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.usecase.account.ListAccountsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ListAccountsController {

    private ListAccountsUseCase listAccountsUseCase;

    @GetMapping("/account")
    public ResponseEntity<Response> listAccounts() {
        var fetchedAccounts = listAccountsUseCase.execute()
                .data()
                .stream()
                .map(account -> new FetchedAccount(account))
                .toList();

        return ResponseEntity.ok(new Response(fetchedAccounts));
    }

    public record Response(List<FetchedAccount> data) {
    }

}
