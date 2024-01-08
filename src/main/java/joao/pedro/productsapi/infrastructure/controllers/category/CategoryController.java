package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.Valid;
import joao.pedro.productsapi.application.usecases.category.CreateCategoryInteractor;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CreateCategoryInteractor createCategoryInteractor;
    private final CategoryDtoMapper categoryDtoMapper;

    @PostMapping
    ResponseEntity<CreateCategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        Category categoryBusinessObj = categoryDtoMapper.toCategory(request);
        Category category = createCategoryInteractor.createCategory(categoryBusinessObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoMapper.toResponse(category));
    }
}
