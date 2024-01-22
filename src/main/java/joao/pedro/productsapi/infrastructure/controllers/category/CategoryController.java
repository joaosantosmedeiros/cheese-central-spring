package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.Valid;
import joao.pedro.productsapi.application.usecases.category.CreateCategoryInteractor;
import joao.pedro.productsapi.application.usecases.category.FindCategoryInteractor;
import joao.pedro.productsapi.application.usecases.category.ListCategoriesInteractor;
import joao.pedro.productsapi.domain.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CreateCategoryInteractor createCategoryInteractor;
    private final FindCategoryInteractor findCategoryInteractor;
    private final ListCategoriesInteractor listCategoriesInteractor;
    private final CategoryDtoMapper categoryDtoMapper;

    @GetMapping("{name}")
    ResponseEntity<CreateCategoryResponse> findOne(@PathVariable("name") String name) {
        Category category = findCategoryInteractor.findCategory(name);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoMapper.toResponse(category));
    }

    @GetMapping
    ResponseEntity<List<CreateCategoryResponse>> findAll() {
        List<Category> categories = listCategoriesInteractor.listCategories();
        List<CreateCategoryResponse> categoryResponse = categories.stream().map(categoryDtoMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }

    @PostMapping
    ResponseEntity<CreateCategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        Category categoryBusinessObj = categoryDtoMapper.toCategory(request);
        Category category = createCategoryInteractor.createCategory(categoryBusinessObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoMapper.toResponse(category));
    }
}
