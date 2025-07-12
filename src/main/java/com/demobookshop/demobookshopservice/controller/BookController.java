package com.demobookshop.demobookshopservice.controller;

import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Validated
@RequestMapping(value = {"/api/v1/book", "/api/v2/books" })
@Tag(name = "Book", description = "Book management API")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Get all books.
   *
   * @return Collection of BookDto
   */
  @Operation(summary = "Get all books", description = "Retrieves a collection of all books in the system.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all books",
      content = @io.swagger.v3.oas.annotations.media.Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(HttpStatus.OK)
  public Collection<BookDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  /**
   * Get the book by ID.
   */
  @Operation(summary = "Get book by ID", description = "Retrieves a book by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the book",
      content = @io.swagger.v3.oas.annotations.media.Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = BookDto.class))),
      @ApiResponse(responseCode = "404", description = "Book not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(HttpStatus.OK)
  public BookDto getBookById(
      @Parameter(description = "ID of the book to retrieve", example = "15", required = true)
      @PathVariable("id")  Long id) {
    return bookService.getBookById(id)
        .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " not found"));
  }

}
