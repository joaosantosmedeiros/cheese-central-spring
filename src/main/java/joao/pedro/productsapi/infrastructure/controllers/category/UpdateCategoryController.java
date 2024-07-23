package joao.pedro.productsapi.infrastructure.controllers.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.SaveCategorySchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.category.UpdateCategoryUseCase;
import joao.pedro.productsapi.usecase.category.UpdateCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories.")
@SecurityRequirement(name = "bearer-key")
public class UpdateCategoryController {

    private final UpdateCategoryUseCase updateCategoryUseCase;

    @PutMapping("/category/{id}")
    @Operation(description = "Category update", summary = "Update a category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = SaveCategorySchema.class))),
            responses = {
                @ApiResponse(responseCode = "200", description = "Success."),
                @ApiResponse(responseCode = "400", description = "Invalid data provided.",
                        content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
                @ApiResponse(responseCode = "404", description = "Category not found.",
                        content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
                @ApiResponse(responseCode = "409", description = "Category already exists.",
                        content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            }
    )
    public ResponseEntity<StandardResponse<Category>> updateCategory(@PathVariable("id") UUID id, @RequestBody @Valid Request request){
        var output = this.updateCategoryUseCase.execute(new Input(new Category(id, request.name)));

        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
                "Category updated sucessfully.",
                true,
                output.data()
        ));
    }

    public record Request(
            @NotBlank
            String name
    ){}

}
