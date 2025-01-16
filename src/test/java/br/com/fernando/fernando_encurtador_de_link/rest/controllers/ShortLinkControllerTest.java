package br.com.fernando.fernando_encurtador_de_link.rest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.fernando.fernando_encurtador_de_link.repositories.ShortLinkRepository;
import br.com.fernando.fernando_encurtador_de_link.rest.requests.ShortLinkRequest;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.FieldResponse;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.error.ValidationErrorResponse;
import br.com.fernando.fernando_encurtador_de_link.services.ShortLinkService;

@WebMvcTest(ShortLinkController.class)
public class ShortLinkControllerTest {
    @MockBean
    private ShortLinkService shortLinkService;

    @MockBean
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSaveShortLinkAndReturnStatus201Created() throws Exception {
        UUID shortLinkID = UUID.randomUUID();
        String originalLinkURL = "http://test.com";
        String shortLinkURL = "shortLink";

        ShortLinkRequest request = new ShortLinkRequest(originalLinkURL);

        ShortLinkResponse response = new ShortLinkResponse(
            shortLinkID,
            originalLinkURL,
            shortLinkURL
        );

        when(shortLinkService.saveShortLink(request)).thenReturn(response);

        MvcResult mvcResult = mockMvc
        .perform(
            post("/short-link")
            .content(objectMapper.writeValueAsString(request))
            .contentType("application/json;charset=UTF-8")
            .accept("application/json;charset=UTF-8")
        )
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(response)))
        .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();

        ShortLinkResponse mcvResultResponse = assertDoesNotThrow(() -> objectMapper
        .readValue(responseString, ShortLinkResponse.class));

        assertEquals(shortLinkID, mcvResultResponse.id());
        assertEquals(originalLinkURL, mcvResultResponse.originalLink());
        assertEquals(shortLinkURL, mcvResultResponse.shortLink());

    }

    @Test
    public void shouldNotSaveShortLinkWithNullOriginalLinkURLAndReturnStatusCode400BadRequest() throws Exception {
        String originalLinkURL = null;
        ShortLinkRequest request = new ShortLinkRequest(originalLinkURL);

        FieldResponse field = FieldResponse.builder()
        .name("url")
        .message("url field is required")
        .build();

        List<FieldResponse> fields = List.of(field);

        var errorResponse = ValidationErrorResponse.builder()
            .path("/short-link")
            .statusCode(400)
            .message("Validation fields error")
            .fields(fields)
            .build();

        mockMvc.perform(
            post("/short-link")
            .content(objectMapper.writeValueAsString(request))
            .contentType("application/json")
            .accept("application/json")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void shouldNotSaveShortLinkWithInvalidOriginalLinkAndReturnStatusCode400BadRequest() throws Exception {
        String originalLinkURL = "invalid-url";
        ShortLinkRequest request = new ShortLinkRequest(originalLinkURL);

        FieldResponse field = FieldResponse.builder()
        .name("url")
        .message("url field should be url format")
        .build();

        List<FieldResponse> fields = List.of(field);

        var errorResponse = ValidationErrorResponse.builder()
            .path("/short-link")
            .statusCode(400)
            .message("Validation fields error")
            .fields(fields)
            .build();

        mockMvc.perform(
            post("/short-link")
            .content(objectMapper.writeValueAsString(request))
            .contentType("application/json")
            .accept("application/json")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void shouldRedirectToOriginalLink() throws Exception {
        String originalLinkURL = "teste.com";
        String shortLinkURL = "shortLink";

        when(shortLinkService.getOriginalLink(shortLinkURL))
        .thenReturn(originalLinkURL);

        mockMvc.perform(
            get("/" + shortLinkURL)
        )
        .andExpect(status().isMovedPermanently())
        .andExpect(header().string("Location", originalLinkURL));
    }

    @Test
    public void shouldNotRedirectToOriginalLinkAndReturnStatusCode404NotFound() throws Exception {
        String shortLinkURL = "shortLink";

        when(shortLinkService.getOriginalLink(shortLinkURL)).thenReturn(null);

        mockMvc.perform(
            get("/" + shortLinkURL)
            .content("text/plain")
        )
        .andExpect(status().isNotFound())
        .andExpect(content().string("<h1>The short link in the URL is not linked to any URL</h1>"));
    }
}