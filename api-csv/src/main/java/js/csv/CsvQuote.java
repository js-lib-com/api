package js.csv;

import js.lang.BugError;

/**
 * Built-in characters used for CSV value enclosing (quote characters) both opening and closing.
 * 
 * @author Iulian Rotaru
 */
public enum CsvQuote implements CharEnum
{
  NONE('\0', '\0'), DOUBLE_QUOTATION('"', '"'), SINGLE_QUOTATION('\'', '\''), PIPE('|', '|'), ROUND_BRACKETS('(', ')'), CURLY_BRACKETS('{', '}'), SQUARE_BRACKETS('[', ']'), ANGLE_BRCKETS('<', '>');

  private char openChar;
  private char closeChar;

  private CsvQuote(char openChar, char closeChar)
  {
    this.openChar = openChar;
    this.closeChar = closeChar;
  }

  @Override
  public char value(int... index)
  {
    if(index.length == 0) {
      throw new BugError("Missing index value.");
    }
    switch(index[0]) {
    case 0:
      return openChar;

    case 1:
      return closeChar;
    }
    throw new BugError("Index out of range.");
  }
}
