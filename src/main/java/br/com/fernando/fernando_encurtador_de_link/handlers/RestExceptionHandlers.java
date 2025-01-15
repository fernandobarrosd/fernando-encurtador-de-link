package br.com.fernando.fernando_encurtador_de_link.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpServletRequest request) {
            HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

            List<Map<String, String>> fields = exception.getFieldErrors()
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
    private Map<String, String> convertFieldErrorToMap(FieldError fieldError) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("name", fieldError.getField());
        fieldMap.put("message", fieldError.getDefaultMessage());
        
        return fieldMap;
    }
}