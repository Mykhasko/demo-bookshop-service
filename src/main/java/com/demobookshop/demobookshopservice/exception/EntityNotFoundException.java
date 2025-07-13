package com.demobookshop.demobookshopservice.exception;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1_345_678L;

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(Throwable cause) {
    super(cause);
  }
}
