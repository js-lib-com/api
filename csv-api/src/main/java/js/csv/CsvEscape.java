package js.csv;

public enum CsvEscape
{
  NONE('\0'), DOUBLE_QUOTATION('"'), SINGLE_QUOTATION('\''), PIPE('|'), SLASH('/'), BACKSLASH('\\');

  private char value;

  private CsvEscape(char value)
  {
    this.value = value;
  }

  public char value()
  {
    return value;
  }
}
