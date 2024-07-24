package joao.pedro.productsapi.infrastructure.controllers.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase;
import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories.")
@SecurityRequirement(name = "bearer-key")
public class DeleteCategoryController {

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Category delete", summary = "Delete a category", responses = {
            @ApiResponse(responseCode = "204", description = "Succcess."),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            @ApiResponse(responseCode = "409", description = "Category is alrady in use.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public void deleteCategory(@PathVariable("id") UUID id){
        this.deleteCategoryUseCase.execute(new Input(id));
    }
}
