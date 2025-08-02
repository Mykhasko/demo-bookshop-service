package com.demobookshop.demobookshopservice.service;

import static com.demobookshop.demobookshopservice.util.converter.BookDtoConverter.bookDtoToBook;
import static com.demobookshop.demobookshopservice.util.converter.BookDtoConverter.bookToBookDto;

import com.demobookshop.demobookshopservice.exception.EntityNotFoundException;
import com.demobookshop.demobookshopservice.model.Book;
import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.repository.BookRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing books in the demo bookshop application. This class provides methods to
 * retrieve books from the repository and convert them to DTOs.
 */
@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * Retrieves all books from the repository and converts them to Collection DTOs.
   *
   * @return a collection of BookDto objects representing all books
   */
  public Collection<BookDto> getAllBooks() {
    return bookRepository.findAll().stream()
        .map(bookToBookDto)
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Retrieves a book by its ID and converts it to a DTO.
   *
   * @param bookId the ID of the book to retrieve
   * @return a BookDto object representing the book with the specified ID
   * @throws EntityNotFoundException if no book is found with the specified ID
   */
  public BookDto getBookById(Long bookId) {
    Book optionalResult = bookRepository
        .findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
    return bookToBookDto.apply(optionalResult);
  }

  /**
   * Adds a new book to the repository and converts it to a DTO.
   *
   * @param newBook the BookDto object representing the new book to add
   * @return a BookDto object representing the added book
   */
  @Transactional(rollbackFor = Exception.class)
  public BookDto addBook(BookDto newBook) {
    Book book = bookDtoToBook.apply(newBook);
    return bookToBookDto.apply(bookRepository.save(book));
  }

  /**
   * Updates an existing book in the repository with new values and converts it to a DTO.
   *
   * @param bookId the ID of the book to update
   * @param newBook the BookDto object containing the new values for the book
   * @return a BookDto object representing the updated book
   */
  @Transactional(rollbackFor = Exception.class)
  public BookDto updateBook(Long bookId, BookDto newBook) {
    // Here you would typically update the fields of the existing book with the new values
    Book existingBook = findBookById(bookId);
    Book updatedBook = bookDtoToBook.apply(newBook);
    copyProperties(updatedBook, existingBook);
    // Save the updated book back to the repository
    return bookToBookDto.apply(bookRepository.save(existingBook));
  }

  /**
   * Deletes a book from the repository by its ID.
   *
   * @param bookId the ID of the book to delete
   * @throws EntityNotFoundException if no book is found with the specified ID
   */
  public void deleteBook(Long bookId) {
    bookRepository.deleteById(bookId);
  }

  /**
   * Finds a book by its ID in the repository.
   *
   * @param bookId the ID of the book to find
   * @return the Book entity if found
   * @throws EntityNotFoundException if no book is found with the specified ID
   */
  private Book findBookById(Long bookId) {
    return bookRepository
        .findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
  }

  /**
   * Copies properties from the source BookDto to the target Book entity.
   *
   * @param source the source BookDto object
   * @param target the target Book entity to update
   */
  private void copyProperties(Book source, Book target) {
    target.setUuid(source.getUuid());
    target.setParentId(source.getParentId());
    target.setParentUuid(source.getParentUuid());
    target.setCatalog(source.isCatalog());
    target.setTitle(source.getTitle());
    target.setAuthor(source.getAuthor());
  }
}
