package js.csv;

/**
 * Built-in characters used for CSV value escaping.
 * 
 * @author Iulian Rotaru
 */
public enum CsvEscape implements CharEnum
{
  NONE('\0'), DOUBLE_QUOTATION('"'), SINGLE_QUOTATION('\''), PIPE('|'), SLASH('/'), BACKSLASH('\\');

  private char value;

  private CsvEscape(char value)
  {
    this.value = value;
  }

  @Override
  public char value(int... index)
  {
    return value;
  }
}
