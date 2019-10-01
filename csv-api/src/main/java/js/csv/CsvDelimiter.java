package js.csv;

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
