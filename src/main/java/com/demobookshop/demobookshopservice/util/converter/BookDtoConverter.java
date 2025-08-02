package com.demobookshop.demobookshopservice.util.converter;

import com.demobookshop.demobookshopservice.model.Book;
import com.demobookshop.demobookshopservice.model.dto.BookDto;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utility class for converting Book entities to Book DTOs. This class provides a static function to
 * convert a Book object to a BookDto object.
 */
public class BookDtoConverter {

  private BookDtoConverter() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Function to convert a Book entity to a BookDto.
   *
   * @param b the Book entity to convert
   * @return a BookDto representation of the Book entity
   */
  public static final Function<Book, BookDto> bookToBookDto = b -> new BookDto(
      b.getId(),
      b.getUuid().toString(),
      b.getParentId(),
      Objects.isNull(b.getParentUuid()) ? null : b.getParentUuid().toString(),
      b.isCatalog(),
      b.getTitle(),
      b.getAuthor());

  /**
   * Function to convert a BookDto to a Book entity.
   *
   * @param b the BookDto to convert
   * @return a Book entity representation convert from the BookDto
   */
  public static final Function<BookDto, Book> bookDtoToBook = b -> new Book(
      null,
      Objects.isNull(b.uuid()) ? null : java.util.UUID.fromString(b.uuid()),
      b.parentId(),
      Objects.isNull(b.parentUuid()) ? null : java.util.UUID.fromString(b.parentUuid()),
      b.isCatalog(),
      b.title(),
      b.author());
}
