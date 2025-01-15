package br.com.fernando.fernando_encurtador_de_link.rest.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.fernando.fernando_encurtador_de_link.rest.requests.ShortLinkRequest;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;
import br.com.fernando.fernando_encurtador_de_link.services.ShortLinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @PostMapping("/short-link")
    public ResponseEntity<ShortLinkResponse> saveShortLink(@RequestBody @Valid ShortLinkRequest request) {
        return ResponseEntity.created(null)
            .body(shortLinkService.saveShortLink(request));
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<String> redirectToOriginalLink(@PathVariable String shortLink) {
        String originalLink = shortLinkService.getOriginalLink(shortLink);

        if (originalLink != null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header(HttpHeaders.LOCATION, originalLink)
            .build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("<h1>The short link in the URL is not linked to any URL</h1>");
    }
}