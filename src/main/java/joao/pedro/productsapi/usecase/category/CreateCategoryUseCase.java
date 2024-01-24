package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;

public class CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public  CreateCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input){
        var categoryExists = categoryGateway.findByName(input.name());
        if(categoryExists.isPresent()){
            return new Output(false, "Category already exists.", null);
        }

        var data = this.categoryGateway.create(new Category(input.name()));
        return new Output(true, "Category created sucessfully", data);
    }

    public record Input(String name){}
    public record Output(Boolean status, String message, Category data){}
}
