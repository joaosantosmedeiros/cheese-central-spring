package joao.pedro.productsapi.infrastructure.controllers.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.usecase.product.UpdateProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UpdateProductController {

    private UpdateProductUseCase updateProductUseCase;

    @PutMapping("/product/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid Request request) {
        var output = updateProductUseCase.execute(new UpdateProductUseCase.Input(
                id,
                request.name,
                request.description,
                request.imageUrl,
                request.price,
                request.categoryId
        ));

        return ResponseEntity.ok(new Response(
                true,
                "Product updated successfully.",
                output.data()
        ));
    }

    public record Request(
        @NotBlank
        String name,
        @NotBlank
        String imageUrl,
        @NotBlank
        String description,
        @Positive
        Double price,
        UUID categoryId
    ) {}

    public record Response(
            Boolean status,
            String message,
            Product data
    ) {}
}

