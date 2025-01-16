package br.com.fernando.fernando_encurtador_de_link.services;

import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;
import br.com.fernando.fernando_encurtador_de_link.graphql.types.ShortLink;
import br.com.fernando.fernando_encurtador_de_link.repositories.ShortLinkRepository;
import br.com.fernando.fernando_encurtador_de_link.rest.requests.ShortLinkRequest;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private final ShortLinkRepository shortLinkRepository;


    public ShortLinkResponse saveShortLink(ShortLinkRequest request) {
        String shortLinkURL = RandomStringUtils.secureStrong()
        .nextAlphanumeric(5, 15);

        ShortLinkEntity shortLink = new ShortLinkEntity(
            request.url(),
            shortLinkURL
        );

        ShortLinkEntity shortLinkSaved = shortLinkRepository.save(shortLink);
        
        return new ShortLinkResponse(shortLinkSaved);
    }

    
    public ShortLink saveShortLink(String url) {
        ShortLinkRequest request = new ShortLinkRequest(url);

        return new ShortLink(saveShortLink(request));
    }

    public String getOriginalLink(String shortLinkURL) {
        Optional<ShortLinkEntity> shortLinkOptional = shortLinkRepository.findByShortLink(shortLinkURL);

        if (shortLinkOptional.isEmpty()) {
            return null;
        }
        
        return shortLinkOptional.get().getOriginalLink();
    }

}