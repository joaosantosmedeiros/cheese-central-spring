package joao.pedro.productsapi.infrastructure.config.springdoc.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "UpdateAccountSchema", description = "Object used for updating an account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountSchema {
    private String username;
    private String password;
}
