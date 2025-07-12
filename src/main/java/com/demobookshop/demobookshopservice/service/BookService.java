package com.demobookshop.demobookshopservice.service;

import com.demobookshop.demobookshopservice.model.dto.BookDto;
import com.demobookshop.demobookshopservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.demobookshop.demobookshopservice.util.converter.BookDtoConverter.bookToBookDto;


@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Collection<BookDto> getAllBooks() {
    return bookRepository.findAll().stream().map(bookToBookDto).collect(Collectors.toUnmodifiableSet());
  }

  public Optional<BookDto> getBookById(Long bookId) {
    return Optional.empty();
  }
}
