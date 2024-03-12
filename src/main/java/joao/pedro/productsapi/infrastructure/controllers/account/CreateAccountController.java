package joao.pedro.productsapi.infrastructure.controllers.account;

import jakarta.validation.Valid;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.usecase.account.CreateAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CreateAccountController {

    private final CreateAccountUseCase createAccountUseCase;

    @PostMapping("/account")
    public ResponseEntity<Response> createAccount(@RequestBody @Valid Request request) {
        var output = createAccountUseCase.execute(new CreateAccountUseCase.Input(
                request.username,
                request.email,
                request.password
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
                true,
                "Account created successfully",
                output.data()
        ));
    }

    public record Request(
            String email,
            String username,
            String password
    ) {}

    public record Response(
            boolean status,
            String message,
            Account data
    ) {}
}
