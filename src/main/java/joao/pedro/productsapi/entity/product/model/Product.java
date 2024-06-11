package joao.pedro.productsapi.entity.product.model;

import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.infrastructure.config.db.schema.CategoryEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.ProductEntity;

import java.util.Objects;
import java.util.UUID;

public class Product {

    public Product(UUID id, String name, String description, String imageUrl, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String description, String imageUrl, Double price, Category category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Category category;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(this.id, product.id) && Objects.equals(this.name, product.name) && Objects.equals(this.description, product.description) && Objects.equals(this.imageUrl, product.imageUrl) && Objects.equals(this.price, product.price) && Objects.equals(this.category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, price, category);
    }

    public ProductEntity toProductEntity(){
        return new ProductEntity(
                this.id,
                this.name,
                this.description,
                this.imageUrl,
                this.price,
                new CategoryEntity(this.category.getId(), this.category.getName())
        );
    }
}
