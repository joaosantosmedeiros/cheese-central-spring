package joao.pedro.productsapi.infrastructure.handlers;

import joao.pedro.productsapi.domain.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.domain.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ErrorResponse {
        private String error;
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> entityAlreadyExists(EntityAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }
}
