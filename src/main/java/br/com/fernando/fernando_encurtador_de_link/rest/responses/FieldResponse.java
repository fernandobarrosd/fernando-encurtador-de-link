package br.com.fernando.fernando_encurtador_de_link.rest.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldResponse {
    private final String name;
    private final String message;    
}