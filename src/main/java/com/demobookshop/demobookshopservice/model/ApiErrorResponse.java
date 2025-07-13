package com.demobookshop.demobookshopservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;

@Schema(name = "APi error response", description = "Details of an API error response")
public record ApiErrorResponse(
    @Schema(description = "Error message", example = "Book with ID 99 not found")
        @JsonProperty("message")
        String message,
    @Schema(description = "Detail description", example = "Book with ID 99 not found")
        @JsonProperty("message")
        String details,
    @Schema(description = "Timestamp of the error", example = "2023-10-01T12:00:00Z")
        @JsonProperty("timestamp")
        String timestamp,
    @Schema(description = "HTTP status code", example = "404") @JsonProperty("statusCode")
        int statusCode)
    implements Serializable {
  @Serial
  private static final long serialVersionUID = 1_987_575L;
}
