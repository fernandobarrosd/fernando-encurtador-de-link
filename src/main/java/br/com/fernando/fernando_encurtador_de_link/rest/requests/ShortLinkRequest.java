package br.com.fernando.fernando_encurtador_de_link.rest.requests;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotNull;

public record ShortLinkRequest(
    @NotNull(message = "url field is required")
    @URL(message = "url field should be url format")
    String url) {}