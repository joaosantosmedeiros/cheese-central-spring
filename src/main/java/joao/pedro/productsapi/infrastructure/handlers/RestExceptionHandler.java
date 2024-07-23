package joao.pedro.productsapi.infrastructure.handlers;

import joao.pedro.productsapi.entity.exceptions.*;
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
    private static class Output {
        private boolean status;
        private String message;
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    private ResponseEntity<Output> entityAlreadyExists(EntityAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Output(false, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Output> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Output(false, exception.getMessage()));
    }

    @ExceptionHandler(ObjectInUseException.class)
    private ResponseEntity<Output> objectInUse(ObjectInUseException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Output(false, exception.getMessage()));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    private ResponseEntity<Output> notAuthorized(NotAuthorizedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Output(false, exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<Output> badRequest(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Output(false, exception.getMessage()));
    }

}
