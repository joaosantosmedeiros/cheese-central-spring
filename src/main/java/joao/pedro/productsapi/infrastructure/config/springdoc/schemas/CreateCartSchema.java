package joao.pedro.productsapi.infrastructure.config.springdoc.schemas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartSchema {
    private UUID productId;
    private int amount;
}
