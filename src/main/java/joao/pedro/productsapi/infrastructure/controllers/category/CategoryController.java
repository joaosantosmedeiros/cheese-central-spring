package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.Valid;
import joao.pedro.productsapi.application.usecases.category.CreateCategoryInteractor;
import joao.pedro.productsapi.application.usecases.category.FindCategoryInteractor;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CreateCategoryInteractor createCategoryInteractor;
    private final FindCategoryInteractor findCategoryInteractor;
    private final CategoryDtoMapper categoryDtoMapper;

    @GetMapping("{name}")
    ResponseEntity<CreateCategoryResponse> findOne(@PathVariable("name") String name) {
        Category category = findCategoryInteractor.findCategory(name);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoMapper.toResponse(category));
    }

    @PostMapping
    ResponseEntity<CreateCategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        Category categoryBusinessObj = categoryDtoMapper.toCategory(request);
        Category category = createCategoryInteractor.createCategory(categoryBusinessObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoMapper.toResponse(category));
    }
}
