package org.vivelibre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vivelibre.constants.Constants;
import org.vivelibre.model.Book;
import org.vivelibre.model.BookDate;
import org.vivelibre.service.BookService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    BookService bookService = new BookService();
    Optional<BookDate> result = bookService.filter("Harry", loadBooks());
    log.info("---------------Libro filtrado-----------------------");
    log.info(result.toString());
  }

  private static List<Book> loadBooks() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      var books = objectMapper.readValue(new File(Constants.FILE_PATH), Book[].class);
      return List.of(books);
    }catch (IOException ex) {
      log.error("Error loading books", ex);
      return List.of();
    }
  }
}