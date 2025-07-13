package com.demobookshop.demobookshopservice.controller;

import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = {"/api/v1/book", "/api/v2/books"})
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
  @Operation(
      summary = "Get all books",
      description = "Retrieves a collection of all books in the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all books",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(
      path = "",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public Collection<BookDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  /** Get the book by ID. */
  @Operation(summary = "Get book by ID", description = "Retrieves a book by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the book",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BookDto.class))),
        @ApiResponse(responseCode = "404", description = "Book not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public BookDto getBookById(
      @Parameter(description = "ID of the book to retrieve", example = "15", required = true)
          @PathVariable("id")
          Long id) {
    return bookService.getBookById(id);
  }

  @Operation(summary = "Add a new book", description = "Adds a new book to the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully added the book",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BookDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(
      path = "",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public BookDto addBook(BookDto newBook) {
    return bookService.addBook(newBook);
  }

  @Operation(summary = "Update book by ID", description = "Updates a book by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated the book",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BookDto.class))),
        @ApiResponse(responseCode = "404", description = "Book not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PutMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public BookDto updateBook(
      @Parameter(description = "ID of the book to update", example = "15", required = true)
          @PathVariable("id")
          Long id,
      @RequestBody BookDto newBook) {
    return bookService.updateBook(id, newBook);
  }

  @Operation(summary = "Delete book by ID", description = "Deletes a book by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the book"),
        @ApiResponse(responseCode = "404", description = "Book not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(
      @Parameter(description = "ID of the book to delete", example = "15", required = true)
          @PathVariable("id")
          Long id) {
    bookService.deleteBook(id);
  }
}
