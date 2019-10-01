package js.csv;

import java.nio.charset.Charset;

/**
 * CSV stream properties.
 * 
 * @author Iulian Rotaru
 */
public interface CsvFormat
{
  /**
   * Character used for value separator. Default value for delimiter character is comma (',').
   * 
   * @return delimiter character.
   */
  char delimiter();

  char comment();

  /**
   * Character used to surround complex values. It is known by multiple names like enclosure, encapsulator or even text
   * qualifier. If quote character is present inside value it should be escaped by doubling it.
   * <p>
   * Default value for quote character should be double quotation mark character('"'). Implementation should return null
   * character ('\0') if quote is disabled.
   * 
   * @return quote character possible null ('\0').
   */
  char openQuote();

  char closeQuote();

  char escape();

  boolean header();

  boolean emptyLines();

  boolean trim();

  String nullValue();

  Charset charset();

  boolean strict();

  /**
   * Set the character used to separate record values, both simple and complex. Given delimiter character should not be
   * present in value itself. If value contains delimiter character then value should be quoted.
   * 
   * @param delimiter character used to separate record values.
   * @return this pointer.
   */
  CsvFormat delimiter(char delimiter);

  /**
   * Set the character used to separate record values. This method just delegates {@link #delimiter(char)}.
   * 
   * @param delimiter values separator.
   * @return this pointer.
   */
  CsvFormat delimiter(CsvDelimiter delimiter);

  CsvFormat comment(char commentChar);

  CsvFormat comment(CsvComment comment);

  CsvFormat quote(char quoteChar);

  CsvFormat quote(char openQuoteChar, char closeQuoteChar);

  CsvFormat quote(CsvQuote quote);

  CsvFormat escape(char escapeChar);

  CsvFormat escape(CsvEscape escape);

  CsvFormat header(boolean header);

  CsvFormat emptyLines(boolean emptyLines);

  /**
   * Enable or disable value white space trimming. Note that if delimiter character is a white space itself, e.g. tab
   * character, it should not be considered for value trimming.
   * 
   * @param trim flag for value white space trimming.
   * @return this pointer.
   */
  CsvFormat trim(boolean trim);

  CsvFormat charset(String charset);

  CsvFormat nullValue(String nullValue);

  CsvFormat strict(boolean strict);
}
