package org.vivelibre.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vivelibre.mapper.BookMapper;
import org.vivelibre.model.Book;
import org.vivelibre.model.BookDate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class BookService {

  private static final Logger log = LoggerFactory.getLogger(BookService.class);

  public Optional<BookDate> filter(String filter, List<Book> books) {

    log.info("---------------Libros sin fecha de publicación-----------------------");
    books.stream()
        .filter(noPublishDate())
        .forEach(this::print);

    log.info("---------------Libros ordenados-----------------------");
    var sortBooks = books.stream()
        .sorted(sortBooks())
        .toList();
    sortBooks.forEach(this::print);

    return books.stream()
        .filter( b -> filterBooks(b, filter))
        .max(sortBooks2())
        .map(BookMapper::bookToBookDateMapper);
  }

  private Long getPublicationTimestamp(Book book) {
    String timeStamp = book.getPublicationTimestamp();
    return Objects.isNull(timeStamp)
        ? null
        : Long.parseLong(book.getPublicationTimestamp());
  }

  private Predicate<Book> noPublishDate() {
    return (b) -> Objects.isNull(b.getPublicationTimestamp());
  }
  private Comparator<Book> sortBooks() {
    return Comparator
        .comparing(this::getPublicationTimestamp, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(this::lengthBio);
  }
  private Comparator<Book> sortBooks2() {
    return Comparator.comparing(this::getPublicationTimestamp, Comparator.nullsFirst(Comparator.naturalOrder()));
  }
  private int lengthBio(Book book) {
    return book.getAuthor().getBio().length();
  }
  private void print(Book book) {
    log.info(book.toString());
  }

  private boolean filterBooks(Book book, String filter) {
    String summary = book.getSummary();
    String title = book.getTitle();
    String bio = book.getAuthor().getBio();
    return summary.contains(filter)
        && title.contains(filter)
        && bio.contains(filter);
  }
}
