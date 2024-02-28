package joao.pedro.productsapi.infrastructure.controllers.product;

import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase.Input;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class FindProductByIdController {

    private final FindProductByIdUseCase findProductByIdUseCase;

    @GetMapping("/product/{id}")
    public ResponseEntity<Response> findProduct (@PathVariable("id") UUID id) {
        var output = findProductByIdUseCase.execute(new Input(id));

        return ResponseEntity.ok(new Response(
                true,
                "Product found successfully.",
                output.data()
        ));
    }

    public record Response(
            Boolean status,
            String message,
            Product data
    ) {}
}
