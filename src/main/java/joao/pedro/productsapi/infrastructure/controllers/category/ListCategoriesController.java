package joao.pedro.productsapi.infrastructure.controllers.category;

import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.usecase.category.ListCategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ListCategoriesController {

    private final ListCategoryUseCase listCategoryUseCase;

    @GetMapping("/category/all")
    public ResponseEntity<Response> listCategories() {
        var output = listCategoryUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(new Response(
                true,
                "Listing all categories.",
                output.data()
        ));
    }

    private record Response(
            Boolean status,
            String message,
            List<Category> data
    ) {}
}
