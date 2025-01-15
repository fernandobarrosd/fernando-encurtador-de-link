package br.com.fernando.fernando_encurtador_de_link.rest.responses.error;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ValidationErrorResponse {
    private final String message;
    private final String path;
    private final Integer statusCode;
    private final List<Map<String, String>> fields;
}