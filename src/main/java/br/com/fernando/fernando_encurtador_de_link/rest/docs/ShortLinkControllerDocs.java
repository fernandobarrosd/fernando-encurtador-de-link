package br.com.fernando.fernando_encurtador_de_link.rest.docs;

import org.springframework.http.ResponseEntity;

import br.com.fernando.fernando_encurtador_de_link.rest.requests.ShortLinkRequest;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.ShortLinkResponse;
import br.com.fernando.fernando_encurtador_de_link.rest.responses.error.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Short link", description = "Short link actions")
public interface ShortLinkControllerDocs {
    @Operation(
        summary = "Generate short link and save",
        method = "POST",
        operationId = "saveShortLink"
    )
    @ApiResponses({
        @ApiResponse(
            description = "Short link is generated and saved with success",
            responseCode = "201",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ShortLinkResponse.class)
            )
        ),

        @ApiResponse(
            description = "Request body fields are invalid",
            responseCode = "400",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ValidationErrorResponse.class
                )
            )
        )
    })
    ResponseEntity<ShortLinkResponse> saveShortLink(ShortLinkRequest request);

    @Operation(
        summary = "Redirect to original link URL",
        method = "GET",
        operationId = "redirectToOriginalLink"
    )
    @ApiResponses({
        @ApiResponse(
            description = "Redirect to original link URL is success",
            responseCode = "301",
            headers = @Header(
                name = "Location",
                description = "Original link URL"
            )
        ),

        @ApiResponse(
            description = "Original link is not exists",
            responseCode = "404",
            content = @Content(
                schema = @Schema(implementation = String.class)
            )
        )
    })
    ResponseEntity<?> redirectToOriginalLink(String shortLink);
}