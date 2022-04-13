package js.csv;

/**
 * Built-in characters used for CSV comment line start.
 * 
 * @author Iulian Rotaru
 */
public enum CsvComment implements CharEnum
{
  NONE('\0'), POUND('#'), SLASH('/'), BACKSLASH('\\'), ASTERISK('*'), QUESTION('?'), EXCLAMATION('!'), COLON(':'), SEMICOLON(';');

  private char value;

  private CsvComment(char value)
  {
    this.value = value;
  }

  public char value(int... unused)
  {
    return value;
  }
}
