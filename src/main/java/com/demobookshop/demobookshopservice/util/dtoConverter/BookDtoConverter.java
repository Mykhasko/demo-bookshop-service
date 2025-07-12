package com.demobookshop.demobookshopservice.util.dtoConverter;

import com.demobookshop.demobookshopservice.model.Book;
import com.demobookshop.demobookshopservice.model.dto.BookDto;

import java.util.function.Function;

public class BookDtoConverter {

  private BookDtoConverter() {
    throw new IllegalStateException("Utility class");
  }

  //
  public static Function<Book, BookDto> bookToBookDto = b -> new BookDto(b.getId(),
      b.getUuid().toString(),
      b.getParentId(),
      b.getParentUuid().toString(),
      b.isCatalog(),
      b.getTitle(),
      b.getAuthor());

}
