package com.demobookshop.demobookshopservice.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "BOOKS")
public class Book {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("Unique identifier for the book - PRIMARY KEY.")
  private Long id;

  @Column(name = "UUID")
  private UUID uuid;

  @Column(name = "PARENT_ID")
  private Long parentId;

  @Column(name = "PARENT_UUID")
  private UUID parentUuid;

  @Column(name = "IS_CATALOG")
  @Comment("Indicates if the entity is a catalog.")
  private boolean isCatalog;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "AUTHOR")
  private String author;

  public Book() {}

  public Book(
      Long id,
      UUID uuid,
      Long parentId,
      UUID parentUuid,
      boolean isCatalog,
      String title,
      String author) {
    this.id = id;
    this.uuid = uuid;
    this.parentId = parentId;
    this.parentUuid = parentUuid;
    this.isCatalog = isCatalog;
    this.title = title;
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public UUID getParentUuid() {
    return parentUuid;
  }

  public void setParentUuid(UUID parentUuid) {
    this.parentUuid = parentUuid;
  }

  public boolean isCatalog() {
    return isCatalog;
  }

  public void setCatalog(boolean catalog) {
    isCatalog = catalog;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return isCatalog == book.isCatalog
        && Objects.equals(uuid, book.uuid)
        && Objects.equals(parentId, book.parentId)
        && Objects.equals(parentUuid, book.parentUuid)
        && Objects.equals(title, book.title)
        && Objects.equals(author, book.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, parentId, parentUuid, isCatalog, title, author);
  }

  @Override
  public String toString() {
    return "Book{" + "id="
        + id + ", uuid="
        + uuid + ", parentId="
        + parentId + ", parentUuid="
        + parentUuid + ", isCatalog="
        + isCatalog + ", title='"
        + title + '\'' + ", author='"
        + author + '\'' + '}';
  }
}
