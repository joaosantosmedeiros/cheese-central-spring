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
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
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
@Tag(name = "Products", description = "Operation related to products.")
@SecurityRequirement(name = "bearer-key")
public class UpdateProductController {

    private UpdateProductUseCase updateProductUseCase;

    @PutMapping("/product/{id}")
    @Operation(description = "Product update", summary = "Update a product", responses = {
            @ApiResponse(
                    responseCode = "200",
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
                    description = "Product/Category does not exists.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
    })
    public ResponseEntity<StandardResponse<Product>> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid Request request) {
        Product product = new Product(
                id,
                request.name,
                request.description,
                request.imageUrl,
                request.price,
                new Category(request.categoryId, null)
        );
        var output = updateProductUseCase.execute(new UpdateProductUseCase.Input(product));

        return ResponseEntity.ok(new StandardResponse<>(
                "Product updated successfully.",
                true,
                output.data()
        ));
    }

    public record Request(
        String name,
        String imageUrl,
        String description,
        Double price,
        UUID categoryId
    ) {}
}

