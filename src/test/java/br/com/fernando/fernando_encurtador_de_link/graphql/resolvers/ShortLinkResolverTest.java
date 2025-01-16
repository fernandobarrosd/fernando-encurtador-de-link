package br.com.fernando.fernando_encurtador_de_link.graphql.resolvers;

import static org.mockito.Mockito.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import br.com.fernando.fernando_encurtador_de_link.config.GraphQLConfig;
import br.com.fernando.fernando_encurtador_de_link.graphql.types.ShortLink;
import br.com.fernando.fernando_encurtador_de_link.services.ShortLinkService;


@GraphQlTest(ShortLinkResolver.class)
@Import(GraphQLConfig.class)
public class ShortLinkResolverTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private ShortLinkService shortLinkService;


    @Test
    public void shouldSaveShortLinkWithSuccess() {
        UUID shortLinkID = UUID.randomUUID();
        String shortLinkURL = "short-link";
        String originalLinkURL = "http://teste.com";

        ShortLink shortLink = new ShortLink(
            shortLinkID,
            originalLinkURL,
            shortLinkURL
        );

        String saveShortLinkMutation = """
                mutation {
                    saveShortLink(url: \"%s\") { id, originalLink, shortLink }
                }
                """.formatted(originalLinkURL);

        when(shortLinkService.saveShortLink(originalLinkURL))
        .thenReturn(shortLink);

        ShortLink shortLinkSaved = graphQlTester.document(saveShortLinkMutation)
            .execute()
            .path("saveShortLink")
            .entity(ShortLink.class)
            .get();

        assertNotNull(shortLinkSaved);

        assertEquals(shortLinkID, shortLinkSaved.getId());
        assertEquals(originalLinkURL, shortLinkSaved.getOriginalLink());
        assertEquals(shortLinkURL, shortLinkSaved.getShortLink());
    }

    @Test
    public void shouldNotSaveShortLinkWithNullOriginalLinkURLAndReturnGraphQLError() {
        String originalLinkURL = null;

        String saveShortLinkMutation = """
                mutation {
                    saveShortLink(url: %s) { id, originalLink, shortLink }
                }
                """.formatted(originalLinkURL);
        
        
        graphQlTester.document(saveShortLinkMutation)
            .execute()
            .errors()
            .expect(error -> error.getMessage().contentEquals("Validation fields error"))
            .expect(error -> error.getPath().contentEquals("saveShortLink"));
    }

    @Test
    public void shouldNotSaveShortLinkWithInvalidOriginalLinkURLAndReturnGraphQLError() {
        String originalLinkURL = "invalid-url";

        String saveShortLinkMutation = """
                mutation {
                    saveShortLink(url: \"%s\") { id, originalLink, shortLink }
                }
                """.formatted(originalLinkURL);
        
        
        graphQlTester.document(saveShortLinkMutation)
            .execute()
            .errors()
            .expect(error -> error.getMessage().contentEquals("Validation fields error"))
            .expect(error -> error.getPath().contentEquals("saveShortLink"));
    }
}