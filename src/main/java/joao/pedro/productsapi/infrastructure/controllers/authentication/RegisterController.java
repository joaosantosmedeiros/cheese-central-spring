package joao.pedro.productsapi.infrastructure.controllers.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.CreateAccountSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
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
@Tag(name = "Authentication", description = "Operations related to authentication.")
public class RegisterController {

    private final CreateAccountUseCase createAccountUseCase;

    @PostMapping("/auth/register")
    @Operation(description = "Account registration", summary = "Register an account.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateAccountSchema.class))))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email/Username already in use.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
    })
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
