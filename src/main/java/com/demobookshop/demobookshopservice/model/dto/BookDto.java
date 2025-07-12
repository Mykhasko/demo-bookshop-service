package com.demobookshop.demobookshopservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @param id
 * @param uuid
 * @param parentId
 * @param parentUuid
 * @param isCatalog
 * @param title
 * @param author
 */
@Schema(name = "Book", description = "Book DTO")
public record BookDto(
    /**
     * Book ID.
     */
    @Schema(description = "Book ID", example = "1")
    @JsonProperty("id")
    Long id,
    @Schema(description = "Book UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("uuid")
    String uuid,
    @Schema(description = "Parent ID", example = "1")
    @JsonProperty("parentId")
    Long parentId,
    @Schema(description = "Parent UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("parentUuid")
    String parentUuid,
    @Schema(description = "Element is Catalog", example = "true")
    @JsonProperty("isCatalog")
    boolean isCatalog,
    @Schema(description = "Book Title", example = "The Great Gatsby")
    @JsonProperty("title")
    String title,
    @Schema(description = "Book Author", example = "F. Scott Fitzgerald")
    @JsonProperty("author")
    String author
) implements Serializable {
  @Serial
  private static final long serialVersionUID = 12L;
}
