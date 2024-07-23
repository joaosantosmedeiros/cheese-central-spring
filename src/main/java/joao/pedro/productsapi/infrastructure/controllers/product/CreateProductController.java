package joao.pedro.productsapi.infrastructure.controllers.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.product.CreateProductUseCase;
import joao.pedro.productsapi.usecase.product.CreateProductUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class CreateProductController {

    private final CreateProductUseCase createProductUseCase;

    @PostMapping("/product")
    public ResponseEntity<StandardResponse<Product>> create(@RequestBody @Valid Request request) {
        var output = createProductUseCase.execute(new Input(
               request.name,
               request.description,
               request.imageUrl,
               request.price,
               request.categoryId
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(
                "Product created successfully.",
                true,
                output.data()
                )
        );
    }

    public record Request(
            @NotBlank
            String name,
            @NotBlank
            String description,
            @NotBlank
            String imageUrl,
            @Positive
            Double price,
            UUID categoryId
    ){}
}
