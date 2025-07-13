package com.demobookshop.demobookshopservice.service;

import static com.demobookshop.demobookshopservice.util.converter.BookDtoConverter.bookDtoToBook;
import static com.demobookshop.demobookshopservice.util.converter.BookDtoConverter.bookToBookDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.demobookshop.demobookshopservice.model.Book;
import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.repository.BookRepository;
import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        new Book(3L, UUID.randomUUID(), 0L, null, true, "Title 3", "Author 3")));
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
    BookDto bookDto = bookService.getBookById(bookId);

    // Assert
    assertNotNull(bookDto, "Book should be present");
    assertEquals(bookId, bookDto.id(), "Book ID should match");
    assertEquals("Title 1", bookDto.title(), "Book title should match");
    assertEquals("Author 1", bookDto.author(), "Book author should match");
  }

  @Test
  void addBook() {
    // Arrange
    BookDto newBookDto =
        new BookDto(null, UUID.randomUUID().toString(), 0L, null, true, "New Title", "New Author");
    Book newBook = bookDtoToBook.apply(newBookDto);
    Book savedBook = bookDtoToBook.apply(newBookDto);
    savedBook.setId(4L); // Simulate the ID being set after saving
    BookDto savedBookDto = bookToBookDto.apply(savedBook);
    when(bookRepository.save(newBook)).thenReturn(savedBook);
    // Act
    BookDto addedBookDto = bookService.addBook(newBookDto);
    // Assert
    assertNotNull(addedBookDto, "Book should not be null");
    assertEquals(savedBookDto.id(), addedBookDto.id(), "Added book ID should match 4L");
    assertEquals(
        0,
        newBookDto.title().compareToIgnoreCase(addedBookDto.title()),
        "Added book title should match");

    verify(bookRepository, times(1)).save(newBook);
    verifyNoMoreInteractions(bookRepository);
  }

  @Test
  void updateBook() {
    // Arrange
    Long bookId = 1L;
    var updatedTitle = "Updated Title";
    var updatedAuthor = "Updated Author";
    var updatedUuid = UUID.randomUUID().toString();
    BookDto updatedBookDto =
        new BookDto(3L, updatedUuid, 0L, null, true, updatedTitle, updatedAuthor);
    Book existingBook =
        new Book(bookId, UUID.randomUUID(), 0L, null, true, "Old Title", "Old Author");
    Book updatedBook = bookDtoToBook.apply(updatedBookDto);
    updatedBook.setId(bookId); // Ensure the ID matches the existing book
    // Mock the repository behavior
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
    when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

    // Act
    BookDto result = bookService.updateBook(bookId, updatedBookDto);

    // Assert
    assertNotNull(result, "Updated book should not be null");
    assertEquals(bookId, result.id(), "Updated book ID should match");
    assertEquals(updatedTitle, result.title(), "Updated book title should match");
    assertEquals(updatedAuthor, result.author(), "Updated book author should match");

    verify(bookRepository, times(1)).findById(bookId);
    verify(bookRepository, times(1)).save(any(Book.class));
    verifyNoMoreInteractions(bookRepository);
  }

  @Test
  void deleteBook() {
    // Arrange
    Long bookId = 1L;
    doNothing().when(bookRepository).deleteById(bookId);

    // Act
    bookService.deleteBook(bookId);

    // Assert
    verify(bookRepository, times(1)).deleteById(bookId);
    verifyNoMoreInteractions(bookRepository);
  }
}
