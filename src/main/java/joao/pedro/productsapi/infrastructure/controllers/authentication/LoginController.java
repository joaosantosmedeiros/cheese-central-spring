package joao.pedro.productsapi.infrastructure.controllers.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.security.TokenService;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.CreateAccountSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.LoginSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "Operations related to authentication.")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @PostMapping("/auth/login")
    @Operation(description = "Account Login", summary = "Login.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginSchema.class))))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid credentials",
                    content = @Content()
            ),
    })
    public ResponseEntity<StandardResponse<String>> login(@RequestBody @Valid Request request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username, request.password);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        AccountEntity accountEntity = (AccountEntity) auth.getPrincipal();
        var token = tokenService.generateToken(new Account(
                accountEntity.getId(),
                accountEntity.getUsername(),
                accountEntity.getEmail(),
                accountEntity.getPassword(),
                false,
                accountEntity.getRole(),
                List.of()
        ));

        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
                "Logged in successfully.",
                true,
                token
        ));
    }

    public record Request(
            @NotBlank
            String username,
            @NotBlank
            String password
    ) {}
}
