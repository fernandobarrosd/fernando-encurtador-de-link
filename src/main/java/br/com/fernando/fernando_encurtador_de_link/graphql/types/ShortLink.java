package br.com.fernando.fernando_encurtador_de_link.graphql.types;

import java.util.UUID;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ShortLink {
    private UUID id;
    private String originalLink;
    private String shortLink;

    public ShortLink(ShortLinkResponse shortLinkResponse) {
        this(
            shortLinkResponse.id(),
            shortLinkResponse.originalLink(),
            shortLinkResponse.shortLink()
        );
    }

    @Override
    public String toString() {
        return "ShortLink(id=%s, originalLink=%s, shortLink=%s)"
        .formatted(id.toString(), originalLink, shortLink);
    }
}