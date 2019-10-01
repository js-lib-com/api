package js.csv;

public enum CsvComment
{
  NONE('\0'), POUND('#'), SLASH('/'), BACKSLASH('\\'), ASTERISK('*'), QUESTION('?'), EXCLAMATION('!'), COLON(':'), SEMICOLON(';');

  private char value;

  private CsvComment(char value)
  {
    this.value = value;
  }

  public char value()
  {
    return value;
  }
}
