package org.vivelibre.constants;

public class Constants {
  private Constants() throws IllegalAccessException {
    throw new IllegalAccessException("Do not instantiate a utility class");
  }
  public static String FILE_PATH = "src\\main\\resources\\books.json";
  public static String DATE_PATTERN = "mm-dd-yyyy";
}
