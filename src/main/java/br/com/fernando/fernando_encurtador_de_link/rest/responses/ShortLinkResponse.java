package br.com.fernando.fernando_encurtador_de_link.rest.responses;

import java.util.UUID;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;

public record ShortLinkResponse(UUID id, String originalLink, String shortLink) {
    public static ShortLinkResponse toResponse(ShortLinkEntity shortLinkEntity) {
        return new ShortLinkResponse(
            shortLinkEntity.getId(),
            shortLinkEntity.getOriginalLink(),
            shortLinkEntity.getShortLink()
        );
    }
}