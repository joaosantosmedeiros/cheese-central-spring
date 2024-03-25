package joao.pedro.productsapi.infrastructure.controllers.account;

import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FindAccountByEmailController {

    private final FindAccountByEmailUseCase findAccountByEmailUseCase;

    @GetMapping("/account/{email}")
    public ResponseEntity<Response> findAccount(@PathVariable(value = "email") String email) {
       var output = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(email));

       return ResponseEntity.ok(new Response(
               true,
               "Account found successfully",
               new FetchedAccount(output.data())
       ));
    }

    public record Response(
            boolean status,
            String message,
            FetchedAccount data
    ) {}
}
