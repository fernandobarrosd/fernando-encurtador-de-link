package br.com.fernando.fernando_encurtador_de_link.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;
import br.com.fernando.fernando_encurtador_de_link.exceptions.EntityNotFoundException;
import br.com.fernando.fernando_encurtador_de_link.graphql.types.ShortLink;
import br.com.fernando.fernando_encurtador_de_link.repositories.ShortLinkRepository;
import br.com.fernando.fernando_encurtador_de_link.rest.requests.ShortLinkRequest;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ShortLinkServiceTest {
    @InjectMocks
    private ShortLinkService shortLinkService;

    @Mock
    private ShortLinkRepository shortLinkRepository;

    @Test
    public void shouldReturnResponseWhenCallSaveShortLink() {
        String shortLinkURL = "shortLink";
        String originalLinkURL = "http://test.com";

        ShortLinkRequest request = new ShortLinkRequest(originalLinkURL);
        ShortLinkEntity shortLink = new ShortLinkEntity(
            UUID.randomUUID(),
            originalLinkURL,
            shortLinkURL
        );

        when(shortLinkRepository.save(any(ShortLinkEntity.class)))
        .thenReturn(shortLink);


        ShortLinkResponse response = shortLinkService.saveShortLink(request);

        assertNotNull(response);

        assertEquals(response.id(), shortLink.getId());
        assertEquals(response.originalLink(), originalLinkURL);
        assertEquals(response.shortLink(), shortLinkURL);

    }

    @Test
    public void shouldReturnShortLinkGraphQLWhenCallSaveShortLink() {
        String shortLinkURL = "shortLink";
        String originalLinkURL = "test.com";
        UUID shortLinkID = UUID.randomUUID();

        ShortLinkEntity shortLinkEntity = new ShortLinkEntity(
            shortLinkID,
            originalLinkURL,
            shortLinkURL
        );

        when(shortLinkRepository.save(any(ShortLinkEntity.class)))
        .thenReturn(shortLinkEntity);

        ShortLink shortLink = shortLinkService.saveShortLink(originalLinkURL);
        
        assertNotNull(shortLink);

        assertEquals(shortLinkID, shortLink.getId());
        assertEquals(shortLinkURL, shortLink.getShortLink());
        assertEquals(originalLinkURL, shortLink.getOriginalLink());
        
    }


    @Test
    public void shouldReturnOriginalLinkWhenCallGetOriginalLink() {
        String shortLinkURL = "shortLink";
        String originalLinkURL = "http://test.com";

        ShortLinkEntity shortLink = new ShortLinkEntity(
            UUID.randomUUID(),
            originalLinkURL,
            shortLinkURL
        );

        when(shortLinkRepository.findByShortLink(shortLinkURL))
        .thenReturn(Optional.of(shortLink));

        String result = shortLinkService.getOriginalLink(shortLinkURL);

        assertEquals(originalLinkURL, result);
    }

    @Test
    public void shouldThrowsEntityNotFoundExceptionWhenCallGetOriginalLink() {
        String shortLinkURL = "shortLink";
        String shortLinkIsNotExistsMessage = "Short link %s is not exists"
        .formatted(shortLinkURL);

        when(shortLinkRepository.findByShortLink(shortLinkURL))
        .thenReturn(Optional.empty());

        EntityNotFoundException exception = 
        assertThrows(
            EntityNotFoundException.class,
            () -> shortLinkService.getOriginalLink(shortLinkURL)
        );


        assertEquals(exception.getMessage(), shortLinkIsNotExistsMessage);

    }
}