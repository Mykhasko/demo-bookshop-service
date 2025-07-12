package com.demobookshop.demobookshopservice.controller;

import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets", uriScheme = "https", uriHost = "api.demobookshop.com")
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BookService bookService;

  /***
  * A sorted set of books for testing
   * (we expect that in {@link BookControllerTest#test_getAllBooks_200_Ok()} the books are sorted by ID,
   * and we expect right order in the response - fist element should be "Catalog one" and the second - "Book Two").
  */
  private Collection<BookDto> books;

  @BeforeEach
  void setUp() {
    // Setup mock behavior for bookService if needed
    books = new TreeSet<>(Comparator.comparing(BookDto::id)) {{
      addAll(Set.of(
          new BookDto(1L, UUID.randomUUID().toString(), 0L, null, true, "Catalog One", "Catalog One"),
          new BookDto(2L, UUID.randomUUID().toString(), 1L, null, false, "Book Two", "Author B")
      ));
    }};
  }

  @Test
  @DisplayName("Get all books by GET /api/v1/book, /api/v2/books")
  void test_getAllBooks_200_Ok() {
    //Arrange
    Collection<BookDto> booksSorted = new TreeSet<>() {{
      addAll(books);
    }};

    when(bookService.getAllBooks()).thenReturn(booksSorted);

    // Act & Assert
    try {
      mockMvc.perform(get("/api/v1/book"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].title").value("Catalog One"))
          .andExpect(jsonPath("$[1].title").value("Book Two"))
          .andDo(document("get-all-books",
              responseFields(
                  fieldWithPath("[].id").description("Book ID"),
                  fieldWithPath("[].uuid").description("Book UUID"),
                  fieldWithPath("[].parentId").description("Parent ID"),
                  fieldWithPath("[].parentUuid").description("Parent UUID"),
                  fieldWithPath("[].isCatalog").description("Element is Catalog"),
                  fieldWithPath("[].title").description("Book Title"),
                  fieldWithPath("[].author").description("Book Author")
              )
          ));

    } catch (Exception e) {
      fail("Exception occurred while getting all books: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Get book by ID by GET /api/v1/book/{id}, /api/v2/books/{id}")
  void test_getBookById_200_Ok() {
    // Arrange
    Long bookId = 1L;
    BookDto book = new BookDto(bookId, UUID.randomUUID().toString(), 0L, null, true, "Catalog One", "Catalog One");
    when(bookService.getBookById(bookId)).thenReturn(java.util.Optional.of(book));

    // Act & Assert
    try {
      mockMvc.perform(get("/api/v1/book/" + bookId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(bookId))
          .andExpect(jsonPath("$.isCatalog").value(true))
          .andExpect(jsonPath("$.title").value("Catalog One"))
          .andExpect(jsonPath("$.author").value("Catalog One"));
    } catch (Exception e) {
      fail("Exception occurred while getting book by ID: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Add new book by POST /api/v1/book, /api/v2/books")
  void test_addNewBook_201_Created() {
    // This test is not implemented yet, as the BookController does not have a POST method.
    // You can implement this test when the POST method is added to the BookController.
    fail("Test for adding new book is not implemented yet.");
  }

  @Test
  @DisplayName("Update book by ID by PUT /api/v1/book/{id}, /api/v2/books/{id}")
  void test_updateBookById_200_Ok() {
    // This test is not implemented yet, as the BookController does not have a PUT method.
    // You can implement this test when the PUT method is added to the BookController.
    fail("Test for updating book by ID is not implemented yet.");
  }

  @Test
  @DisplayName("Delete book by ID by DELETE /api/v1/book/{id}, /api/v2/books/{id}")
  void test_deleteBookById_204_No_Content() {
    // This test is not implemented yet, as the BookController does not have a DELETE method.
    // You can implement this test when the DELETE method is added to the BookController.
    fail("Test for deleting book by ID is not implemented yet.");
  }

  @Test
  @DisplayName("Get book by ID not found by GET /api/v1/book/{id}, /api/v2/books/{id}")
  void test_getBookByIdNotFound_404_Not_Found() {
    // Arrange
    Long bookId = 999L; // Non-existing book ID
    when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

    // Act & Assert
    try {
      mockMvc.perform(get("/api/v1/book/" + bookId))
          .andExpect(status().isNotFound());
    } catch (Exception e) {
      fail("Exception occurred while getting non-existing book by ID: " + e.getMessage());
    }
  }

}