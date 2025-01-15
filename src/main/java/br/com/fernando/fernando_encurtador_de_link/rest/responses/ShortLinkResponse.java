package br.com.fernando.fernando_encurtador_de_link.rest.responses;

import java.util.UUID;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;

public record ShortLinkResponse(UUID id, String originalLink, String shortLink) {
    public ShortLinkResponse(ShortLinkEntity shortLinkEntity) {
        this(
            shortLinkEntity.getId(),
            shortLinkEntity.getOriginalLink(),
            shortLinkEntity.getShortLink()
        );
    }

    @Override
    public String toString() {
        return "ShortLinkResponse(id=%s, originalLink=%s, shortLink=%s)"
        .formatted(id.toString(), originalLink, shortLink);
    }
}