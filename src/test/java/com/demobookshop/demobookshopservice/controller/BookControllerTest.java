package com.demobookshop.demobookshopservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demobookshop.demobookshopservice.exception.EntityNotFoundException;
import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.service.BookService;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
@AutoConfigureRestDocs(
    outputDir = "build/generated-snippets",
    uriScheme = "https",
    uriHost = "api.demobookshop.com")
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BookService bookService;

  /**
   * * A sorted set of books for testing (we expect that in
   * {@link BookControllerTest#test_getAllBooks_200_Ok()} the books are sorted by ID, and we expect
   * right order in the response - fist element should be "Catalog one" and the second - "Book
   * Two").
   */
  private Collection<BookDto> books;

  @BeforeEach
  void setUp() {
    // Setup mock behavior for bookService if needed
    books = new TreeSet<>(Comparator.comparing(BookDto::id)) {
      {
        addAll(Set.of(
            new BookDto(
                1L, UUID.randomUUID().toString(), 0L, null, true, "Catalog One", "Catalog One"),
            new BookDto(
                2L, UUID.randomUUID().toString(), 1L, null, false, "Book Two", "Author B")));
      }
    };
  }

  @Test
  @DisplayName("Get all books by GET /api/v1/book, /api/v2/books")
  void test_getAllBooks_200_Ok() {
    // Arrange
    Collection<BookDto> booksSorted = new TreeSet<>() {
      {
        addAll(books);
      }
    };

    when(bookService.getAllBooks()).thenReturn(booksSorted);

    // Act & Assert
    try {
      mockMvc
          .perform(get("/api/v1/book"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].title").value("Catalog One"))
          .andExpect(jsonPath("$[1].title").value("Book Two"))
          .andDo(document(
              "get-all-books",
              responseFields(
                  fieldWithPath("[].id").description("Book ID"),
                  fieldWithPath("[].uuid").description("Book UUID"),
                  fieldWithPath("[].parentId").description("Parent ID"),
                  fieldWithPath("[].parentUuid").description("Parent UUID"),
                  fieldWithPath("[].isCatalog").description("Element is Catalog"),
                  fieldWithPath("[].title").description("Book Title"),
                  fieldWithPath("[].author").description("Book Author"))));

    } catch (Exception e) {
      fail("Exception occurred while getting all books: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Get book by ID by GET /api/v1/book/{id}, /api/v2/books/{id}")
  void test_getBookById_200_Ok() {
    // Arrange
    Long bookId = 1L;
    BookDto book = new BookDto(
        bookId, UUID.randomUUID().toString(), null, null, false, "Book One", "Author One");

    when(bookService.getBookById(bookId)).thenReturn(book);

    // Act & Assert
    try {
      mockMvc
          .perform(get("/api/v1/book/{id}", bookId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(bookId))
          .andExpect(jsonPath("$.isCatalog").value(true))
          .andExpect(jsonPath("$.title").value("Catalog One"))
          .andExpect(jsonPath("$.author").value("Catalog One"))
          .andDo(document(
              "get-book-by-id",
              pathParameters(parameterWithName("id").description("ID of the book to retrieve")),
              responseFields(
                  fieldWithPath("id").description("Book ID"),
                  fieldWithPath("uuid").description("Book UUID"),
                  fieldWithPath("parentId").description("Parent ID"),
                  fieldWithPath("parentUuid").description("Parent UUID"),
                  fieldWithPath("isCatalog").description("Element is Catalog"),
                  fieldWithPath("title").description("Book Title"),
                  fieldWithPath("author").description("Book Author"))));

    } catch (Exception e) {
      fail("Exception occurred while getting book by ID: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Add new book by POST /api/v1/book, /api/v2/books")
  void test_addNewBook_201_Created() {
    // Arrange
    BookDto newBook =
        new BookDto(5L, UUID.randomUUID().toString(), 0L, null, false, "New Book", "New Author");
    when(bookService.addBook(newBook)).thenReturn(newBook);
    // Act & Assert
    try {
      mockMvc
          .perform(post("/api/v1/book")
              .contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsString(newBook)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.title").value("New Book"))
          .andExpect(jsonPath("$.author").value("New Author"))
          .andDo(document(
              "add-new-book",
              requestFields(
                  fieldWithPath("id").description("Book ID"),
                  fieldWithPath("uuid").description("Book UUID"),
                  fieldWithPath("parentId").description("Parent ID"),
                  fieldWithPath("parentUuid").description("Parent UUID"),
                  fieldWithPath("isCatalog").description("Element is Catalog"),
                  fieldWithPath("title").description("Book Title"),
                  fieldWithPath("author").description("Book Author")),
              responseFields(
                  fieldWithPath("id").description("Book ID"),
                  fieldWithPath("uuid").description("Book UUID"),
                  fieldWithPath("parentId").description("Parent ID"),
                  fieldWithPath("parentUuid").description("Parent UUID"),
                  fieldWithPath("isCatalog").description("Element is Catalog"),
                  fieldWithPath("title").description("Book Title"),
                  fieldWithPath("author").description("Book Author"))));
    } catch (Exception e) {
      fail("Exception occurred while adding new book: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Update book by ID by PUT /api/v1/book/{id}, /api/v2/books/{id}")
  void test_updateBookById_200_Ok() {
    // Arrange
    BookDto updatedBook = new BookDto(
        2L, UUID.randomUUID().toString(), 0L, null, true, "Updated Book", "Updated Author");
    when(bookService.updateBook(1L, updatedBook)).thenReturn(updatedBook);
    // Act & Assert
    try {
      mockMvc
          .perform(put("/api/v1/book/1")
              .contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsString(updatedBook)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.title").value("Updated Book"))
          .andExpect(jsonPath("$.author").value("Updated Author"))
          .andDo(document(
              "update-book-by-id",
              pathParameters(parameterWithName("id").description("ID of the book to update")),
              requestFields(
                  fieldWithPath("id").description("Book ID"),
                  fieldWithPath("uuid").description("Book UUID"),
                  fieldWithPath("parentId").description("Parent ID"),
                  fieldWithPath("parentUuid").description("Parent UUID"),
                  fieldWithPath("isCatalog").description("Element is Catalog"),
                  fieldWithPath("title").description("Book Title"),
                  fieldWithPath("author").description("Book Author")),
              responseFields(
                  fieldWithPath("id").description("Book ID"),
                  fieldWithPath("uuid").description("Book UUID"),
                  fieldWithPath("parentId").description("Parent ID"),
                  fieldWithPath("parentUuid").description("Parent UUID"),
                  fieldWithPath("isCatalog").description("Element is Catalog"),
                  fieldWithPath("title").description("Book Title"),
                  fieldWithPath("author").description("Book Author"))));
    } catch (Exception e) {
      fail("Exception occurred while updating book by ID: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Delete book by ID by DELETE /api/v1/book/{id}, /api/v2/books/{id}")
  void test_deleteBookById_204_No_Content() {
    // Arrange
    // Act & Assert
    try {
      mockMvc
          .perform(delete("/api/v1/book/{id}", 1L))
          .andExpect(status().isNoContent())
          .andDo(document(
              "delete-book-by-id",
              pathParameters(parameterWithName("id").description("ID of the book to delete"))));
    } catch (Exception e) {
      fail("Exception occurred while deleting book by ID: " + e.getMessage());
    }

    verify(bookService).deleteBook(1L);
    verify(bookService, times(1)).deleteBook(1L);
    verifyNoMoreInteractions(bookService);
  }

  @Test
  @DisplayName("Get book by ID not found by GET /api/v1/book/{id}, /api/v2/books/{id}")
  void test_getBookByIdNotFound_404_Not_Found() {
    // Arrange
    Long bookId = 999L; // Non-existing book ID
    when(bookService.getBookById(bookId))
        .thenThrow(new EntityNotFoundException("Book with ID " + bookId + " not found"));

    // Act & Assert
    try {
      mockMvc.perform(get("/api/v1/book/" + bookId)).andExpect(status().isNotFound());
    } catch (Exception e) {
      fail("Exception occurred while getting non-existing book by ID: " + e.getMessage());
    }
    verify(bookService).getBookById(bookId);
  }
}
