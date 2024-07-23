package joao.pedro.productsapi.infrastructure.dtos;

public record StandardResponse<T>(
        String message,
        Boolean status,
        T data
) {}