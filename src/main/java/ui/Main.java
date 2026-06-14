package ui;

import java.util.Locale;

public class Main {
  public static void main(String[] args) {
      Locale locale = args.length > 0 ? Locale.forLanguageTag(args[0]) : Locale.getDefault();
      new ConsoleUI(locale).start();
  }
}
