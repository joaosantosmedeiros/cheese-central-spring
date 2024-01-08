package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(@NotBlank String name) {
}
