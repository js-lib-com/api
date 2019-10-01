package js.csv;

public enum CsvQuote
{
  NONE('\0', '\0'), DOUBLE_QUOTATION('"', '"'), SINGLE_QUOTATION('\'', '\''), PIPE('|', '|'), ROUND_BRACKETS('(', ')'), CURLY_BRACKETS('{',
      '}'), SQUARE_BRCKETS('[', ']'), ANGLE_BRCKETS('<', '>');

  private char open;
  private char close;

  private CsvQuote(char open, char close)
  {
    this.open = open;
    this.close = close;
  }

  public char open()
  {
    return open;
  }

  public char close()
  {
    return close;
  }
}
