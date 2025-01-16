package br.com.fernando.fernando_encurtador_de_link.graphql.resolvers;

import org.hibernate.validator.constraints.URL;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import br.com.fernando.fernando_encurtador_de_link.graphql.types.ShortLink;
import br.com.fernando.fernando_encurtador_de_link.services.ShortLinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShortLinkResolver {
    private final ShortLinkService shortLinkService;

    @MutationMapping
    public ShortLink saveShortLink(
        @Argument
        @Valid
        @NotNull(message = "url field is required")
        @URL(message = "url field should be url format")
        String url) {
            return shortLinkService.saveShortLink(url);
    }
}