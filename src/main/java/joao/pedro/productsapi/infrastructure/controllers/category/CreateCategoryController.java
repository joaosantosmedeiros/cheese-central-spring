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
import joao.pedro.productsapi.usecase.category.CreateCategoryUseCase;
import joao.pedro.productsapi.usecase.category.CreateCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories.")
@SecurityRequirement(name = "bearer-key")
public class CreateCategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    @PostMapping("/category")
    @Operation(
            description = "Category creation", summary = "Create a category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveCategorySchema.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category created successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided.",
                            content = @Content(schema = @Schema(implementation = StandardResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Category already exists.",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            }
    )
    public ResponseEntity<StandardResponse<Category>> create(@RequestBody @Valid Request request){
        var output = createCategoryUseCase.execute(new Input(request.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(
                "Category created sucessfully.",
                true,
                output.data())
        );
    }

    public record Request(
            @NotBlank
            String name
    ){}
}
