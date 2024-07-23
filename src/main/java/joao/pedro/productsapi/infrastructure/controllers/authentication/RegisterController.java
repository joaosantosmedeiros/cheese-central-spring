package joao.pedro.productsapi.infrastructure.controllers.authentication;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.account.CreateAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RegisterController {

    private final CreateAccountUseCase createAccountUseCase;

    @PostMapping("/auth/register")
    public ResponseEntity<StandardResponse<FetchedAccount>> createAccount(@RequestBody @Valid Request request) {
        var output = createAccountUseCase.execute(new CreateAccountUseCase.Input(
                request.username,
                request.email,
                request.password
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(
                "Account created successfully",
                true,
                output.data()
        ));
    }

    public record Request(
            @Email
            @NotBlank
            String email,
            @NotBlank
            String username,
            @NotBlank
            String password
    ) {}
}
