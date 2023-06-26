package org.vivelibre.mapper;

import org.vivelibre.constants.Constants;
import org.vivelibre.model.Book;
import org.vivelibre.model.BookDate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BookMapper {

  private BookMapper() throws IllegalAccessException {
    throw new IllegalAccessException("Do not instantiate a utility class");
  }

  public static BookDate bookToBookDateMapper(Book book) {
    var bookDate = new BookDate();
    bookDate.setId(book.getId());
    bookDate.setTitle(book.getTitle());
    bookDate.setPublicationTimestamp(book.getPublicationTimestamp());
    bookDate.setPages(book.getPages());
    bookDate.setSummary(book.getSummary());
    bookDate.setAuthor(book.getAuthor());

    bookDate.setDate(Objects.isNull(book.getPublicationTimestamp())
        ? null
        : buildDate(book));

    return bookDate;
  }

  private static String buildDate(Book book) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
    long longTimeStamp = Long.parseLong(book.getPublicationTimestamp());
    Timestamp ts = new Timestamp(longTimeStamp);
    Date date = new Date(ts.getTime());
    return simpleDateFormat.format(date);
  }
}
