package js.csv;

/**
 * Built-in characters that can be used as delimiter for CSV values.
 * 
 * @author Iulian Rotaru
 */
public enum CsvDelimiter
{
  COMMA(','), TAB('\t'), SPACE(' '), DOT('.'), COLON(':'), SEMICOLON(';');

  private char value;

  private CsvDelimiter(char value)
  {
    this.value = value;
  }

  public char value()
  {
    return value;
  }
}
