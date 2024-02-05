package joao.pedro.productsapi.infrastructure.controllers.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @PostMapping("/product/create")
    public ResponseEntity<Object> create(@RequestBody @Valid Request request) {
        var output = createProductUseCase.execute(new Input(
               request.name,
               request.description,
               request.imageUrl,
               request.price,
               request.categoryId
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
                true,
                "Product created successfully.",
                new FetchedProduct(
                        output.data().getId(),
                        output.data().getName(),
                        output.data().getDescription(),
                        output.data().getImageUrl(),
                        output.data().getPrice(),
                        output.data().getCategoryId())
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

    public record Response(
            Boolean status,
            String message,
            FetchedProduct data
    ){}

    public record FetchedProduct(
            UUID id,
            String name,
            String description,
            String imageUrl,
            Double price,
            UUID categoryId
    ){}
}
