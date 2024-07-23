package joao.pedro.productsapi.infrastructure.controllers.authentication;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.security.TokenService;
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
public class LoginController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @PostMapping("/auth/login")
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
