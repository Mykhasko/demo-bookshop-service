package com.demobookshop.demobookshopservice.service;

import com.demobookshop.demobookshopservice.model.Book;
import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookService bookService;

  private List<Book> mockBooks() {
    return new ArrayList<Book>(List.of(
        new Book(1L, UUID.randomUUID(), 0L, null, true, "Title 1", "Author 1"),
        new Book(2L, UUID.randomUUID(), 0L, null, false, "Title 2", "Author 2"),
        new Book(3L, UUID.randomUUID(), 0L, null, true, "Title 3", "Author 3")
    ));
  }

  @Test
  @DisplayName("Get all books (BookService.getAllBooks())")
  void test_getAllBooks() {
    // Arrange
    when(bookRepository.findAll()).thenReturn((List<Book>) mockBooks());
    // Act
    Collection<BookDto> books = bookService.getAllBooks();
    // Assert
    assertNotNull(books, "Books should not be null");
    assertEquals(3, books.size(), "Books collection should size 3!");
  }

  @Test
  @DisplayName("Get book by ID (BookService.getBookById(Long bookId))")
  void getBookById() {
    // Arrange
    Long bookId = 1L;
    Book book = new Book(bookId, UUID.randomUUID(), 0L, null, true, "Title 1", "Author 1");
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

    // Act
    Optional<BookDto> bookDto = bookService.getBookById(bookId);

    // Assert
    assertTrue(bookDto.isPresent(), "Book should be present");
    assertEquals(bookId, bookDto.get().id(), "Book ID should match");
    assertEquals("Title 1", bookDto.get().title(), "Book title should match");
    assertEquals("Author 1", bookDto.get().author(), "Book author should match");
  }
}