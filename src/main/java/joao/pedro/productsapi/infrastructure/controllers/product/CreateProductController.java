package joao.pedro.productsapi.infrastructure.controllers.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
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
@Tag(name = "Products", description = "Operation related to products.")
@SecurityRequirement(name = "bearer-key")
public class CreateProductController {

    private final CreateProductUseCase createProductUseCase;

    @PostMapping("/product")
    @Operation(description = "Product creation", summary = "Create a product", responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden access",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category does not exists.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
    })
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
