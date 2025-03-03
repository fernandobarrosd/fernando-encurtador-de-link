package br.com.fernando.fernando_encurtador_de_link.handlers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import br.com.fernando.fernando_encurtador_de_link.rest.responses.FieldResponse;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpServletRequest request) {
            HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

            List<FieldResponse> fields = exception.getFieldErrors()
            .stream()
            .map((field) -> convertFieldErrorToMap(field))
            .toList();

            var validationErrorResponse = ValidationErrorResponse.builder()
                .message("Validation fields error")
                .path(request.getRequestURI())
                .statusCode(httpStatusCode.value())
                .fields(fields)
                .build();
            
            return ResponseEntity.badRequest()
                .body(validationErrorResponse);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFound(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("The %s URL is not exists".formatted(request.getRequestURI()));
    }
    private FieldResponse convertFieldErrorToMap(FieldError fieldError) {
        return FieldResponse.builder()
        .name(fieldError.getField())
        .message(fieldError.getDefaultMessage())
        .build();
    }
}