package joao.pedro.productsapi.infrastructure.controllers.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.usecase.product.DeleteProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Products", description = "Operation related to products.")
@SecurityRequirement(name = "bearer-key")
public class DeleteProductController {

    private final DeleteProductUseCase deleteProductUseCase;

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Product deletion", summary = "Delete a product.", responses ={
            @ApiResponse(
                    responseCode = "204",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden access."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))
            ),
    })
    void deleteProduct(@PathVariable("id") UUID id ) {
        deleteProductUseCase.execute(new DeleteProductUseCase.Input(id));
    }
}


