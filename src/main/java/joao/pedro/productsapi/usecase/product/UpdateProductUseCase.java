package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;

public class UpdateProductUseCase {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public UpdateProductUseCase(ProductGateway productGateway, CategoryGateway categoryGateway) {
        this.productGateway = productGateway;
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input) {
        var productExists = productGateway.findById(input.product.getId()).orElseThrow(() -> new EntityNotFoundException("Product"));
        var inputProduct = input.product;

        if(inputProduct.getPrice() != null){
            if(inputProduct.getPrice() <= 0) throw new BadRequestException("Price must be positive.");
            productExists.setPrice(inputProduct.getPrice());
        }
        if(inputProduct.getCategory().getId() != null){
            var categoryExists = categoryGateway.findById(input.product.getCategory().getId()).orElseThrow(() -> new EntityNotFoundException("Category"));
            productExists.setCategory(categoryExists);
        }
        if(inputProduct.getImageUrl() != null && !inputProduct.getImageUrl().trim().isBlank()){
            productExists.setImageUrl(inputProduct.getImageUrl());
        }
        if(inputProduct.getDescription() != null && !inputProduct.getDescription().trim().isBlank()){
            productExists.setDescription(inputProduct.getDescription());
        }
        if(inputProduct.getName() != null && !inputProduct.getName().trim().isBlank()){
            productExists.setName(inputProduct.getName());
        }

        return new Output(productGateway.create(productExists));
    }

    public record Input(Product product) {}

    public record Output(Product data) {}
}
