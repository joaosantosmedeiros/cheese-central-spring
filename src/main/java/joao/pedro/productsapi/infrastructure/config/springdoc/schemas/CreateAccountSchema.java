package joao.pedro.productsapi.infrastructure.config.springdoc.schemas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountSchema {

    private String username;
    private String email;
    private String password;
}
