package com.jslib.api.csv;

import java.nio.charset.Charset;

/**
 * CSV stream properties like chars used for delimiter, quote and escaping.
 * 
 * @author Iulian Rotaru
 */
public interface CsvFormat
{
  /**
   * Set the character used to separate record values, both simple and complex. Given delimiter character should not be
   * present in value itself. If value contains delimiter character then value should be quoted.
   * <p>
   * Delimiter cannot be null character ('\0'). Implementation should throw illegal argument exception if delimiter
   * argument is null character.
   * 
   * @param delimiter character used to separate record values.
   * @return this pointer.
   * @throws IllegalArgumentException if <code>delimiter</code> argument is null character ('\0').
   */
  CsvFormat delimiter(char delimiter) throws IllegalArgumentException;

  /**
   * Set the character used to separate record values. This method is a type alternative of {@link #delimiter(char)}.
   * 
   * @param delimiter values separator.
   * @return this pointer.
   */
  CsvFormat delimiter(CsvDelimiter delimiter);

  /**
   * Character used for value separator. Default value for delimiter character is comma (',').
   * 
   * @return delimiter character.
   * @see #delimiter(char)
   * @see #delimiter(CsvDelimiter)
   */
  char delimiter();

  /**
   * Set the character used to mark comment line. If this char is found at the very beginning of a new CSV record that
   * line is considered comment till CRLF.
   * <p>
   * Comment line mark can be null character ('\0') in order to disable comment lines processing.
   * 
   * @param commentChar comment line mark, possible null character ('\0').
   * @return this pointer.
   */
  CsvFormat comment(char commentChar);

  /**
   * Type safe alternative of the {@link #comment(char)} setter.
   * 
   * @param comment comment line mark.
   * @return this pointer.
   */
  CsvFormat comment(CsvComment comment);

  /**
   * Get the character used to mark comment line. Default value for comment character is hash ('#'). Implementation
   * should return null character ('\0') if comment lines processing is disabled.
   * 
   * @return line comment mark, possible null character ('\0').
   * @see #comment(char)
   * @see #comment(CsvComment)
   */
  char comment();

  /**
   * Set the character used to surround complex values. It is known by multiple names like enclosure, encapsulator or
   * even text qualifier. If quote character is present inside value it should be escaped with {@link #escape(char)}
   * character.
   * <p>
   * There are actually two quote characters - opening and closing quote characters, that could be different. This
   * setter uses the same character for both, for example double quotation mark ('"').
   * <p>
   * Quote argument can be null character ('\0') in order to disable value quotation.
   * 
   * @param quoteChar quote character, possible null ('\0').
   * @return this pointer.
   */
  CsvFormat quote(char quoteChar);

  /**
   * Quote character setter used when opening and closing quote character are different. For example square brackets use
   * ('[') and (']') characters.
   * <p>
   * Arguments cannot be null character ('\0'). Use <code>quote('\0');</code> or <code>quote(CsvQuote.NONE);</code> if
   * need to disable value quotation.
   * 
   * @param openQuoteChar opening quote character,
   * @param closeQuoteChar closing quote character.
   * @return this pointer.
   */
  CsvFormat quote(char openQuoteChar, char closeQuoteChar);

  /**
   * Type safe setter for quote characters. This setter covers both conditions: the same and different characters for
   * opening and closing quotes.
   * 
   * @param quote quote characters predefined constant.
   * @return this pointer.
   */
  CsvFormat quote(CsvQuote quote);

  /**
   * Character used to mark start of complex values. Note that a complex value is one containing values and records
   * separators. For this reason complex values should be enclosed between quote characters.
   * <p>
   * Default value for start quote character should be double quotation mark ('"'). Implementation should return null
   * character ('\0') if complex value quote is disabled.
   * 
   * @return start quote character, possible null character ('\0').
   * @see #quote(char)
   * @see #quote(char, char)
   * @see #quote(CsvQuote)
   */
  char openQuote();

  /**
   * Character used to mark end of complex values. Note that a complex value is one containing values and records
   * separators. For this reason complex values should be enclosed between quote characters.
   * <p>
   * Default value for close quote character should be double quotation mark ('"'). Implementation should return null
   * character ('\0') if complex value quote is disabled.
   * <p>
   * It is legal to use the same character for both opening and closing quote. In fact default value is indeed the same
   * double quotation mark character ('"') for both opening and closing.
   * 
   * @return close quote character, possible null character ('\0').
   * @see #quote(char)
   * @see #quote(char, char)
   * @see #quote(CsvQuote)
   */
  char closeQuote();

  /**
   * Set the character used to escape stop quote character inside complex values. Also escape itself, if present into
   * complex value. For default settings both stop quote and escape characters are double quotation mark ('"'). If stop
   * quote character is present on complex value it will be represented on CSV stream as <code>""</code>.
   * <p>
   * Escape argument can be null character ('\0') in order to disable escaping.
   * 
   * @param escapeChar escape character, possible null ('\0').
   * @return this pointer.
   */
  CsvFormat escape(char escapeChar);

  /**
   * Type safe variant of the {@link #escape(char)} setter.
   * 
   * @param escape escape enumeration constant.
   * @return this pointer.
   */
  CsvFormat escape(CsvEscape escape);

  /**
   * Get escape character. Default value is double quotation mark ('"'). Implementation should return null character if
   * escaping is disabled.
   * 
   * @return escape character, possible null ('\0').
   * @see #escape(char)
   * @see #escape(CsvEscape)
   */
  char escape();

  /**
   * Enable or disable header processing. Header should be enabled only if CSV stream indeed contains it, otherwise
   * first records can be lost. There is not reliable method to detect header presence on CSV stream so it is developer
   * responsibility to set this flag correctly.
   * <p>
   * By default header processing is disabled.
   * 
   * @param header header enabling flag.
   * @return this pointer.
   */
  CsvFormat header(boolean header);

  /**
   * Get the state of header processing flag as set by {@link #header(boolean)}. Default value is false.
   * 
   * @return header processing flag.
   * @see #header(boolean)
   */
  boolean header();

  /**
   * Enable or disable empty lines processing. By default empty lines are not considered valid records and are discarded
   * by CSV reader.
   * <p>
   * If this flag is enabled, empty lines are considered valid CSV records. Since there is no value, record related
   * object instance has all fields zero, false or null - as initialized by JVM.
   * 
   * @param emptyLines flag for empty lines processing.
   * @return this pointer.
   */
  CsvFormat emptyLines(boolean emptyLines);

  /**
   * Get the state of empty lines processing flag. Default value is false, in which case empty lines are discarded.
   * 
   * @return empty lines processing flag.
   * @see #emptyLines(boolean)
   */
  boolean emptyLines();

  /**
   * Enable or disable value white space trimming. Note that if delimiter character is a white space itself, e.g. tab
   * character, it should not be considered for value trimming.
   * <p>
   * By default white space trimming is enabled.
   * 
   * @param trim flag for value white space trimming.
   * @return this pointer.
   */
  CsvFormat trim(boolean trim);

  /**
   * Get the state of white space trimming. By default white space trim flag is true.
   * 
   * @return white space trimming flag value.
   * @see #trim(boolean)
   */
  boolean trim();

  /**
   * Set value to be used for null fields value. When CSV reader encounter this <code>null value</code> it should leave
   * record object instance field as initialized by JVM. Note that reader should ignore case when comparing for
   * <code>null value</code>.
   * <p>
   * On CSV writing, if record object field is null CSV value should be replaced by this <code>null value</code> string.
   * Note that fields with zero and false are not null.
   * <p>
   * Default value for null field is <code>NULL</code>.
   * 
   * @param nullValue null fields value.
   * @return this pointer.
   */
  CsvFormat nullValue(String nullValue);

  /**
   * Get string value to be used for null fields value. By default this value is <code>NULL</code>.
   * 
   * @return string value for null fields.
   * @see #nullValue(String)
   */
  String nullValue();

  /**
   * Set character encoding to be used when read or write CSV streams.
   * <p>
   * Charset value is actually used when underlying CSV readers and writers use byte streams for source, respective
   * target streams - see {@link CsvFactory#getReader(CsvDescriptor, java.io.InputStream)} and
   * {@link CsvFactory#getWriter(CsvDescriptor, java.io.OutputStream)}.
   * <p>
   * If underlying stream is characters stream, that is Java {@link java.io.Reader} and {@link java.io.Writer}, they use
   * system default character encoding and ignore this setting.
   * <p>
   * Default value for character encoding is UTF-8.
   * 
   * @param charset character encoding on CSB stream.
   * @return this pointer.
   */
  CsvFormat charset(String charset);

  /**
   * Get character encoding used when underlying CSV reader or writer stream is a byte stream.
   * 
   * @return character encoding for CSV byte stream.
   */
  Charset charset();

  /**
   * Enable or disable strict mode. Is recommended for implementation to throw exceptions only in strict mode; on
   * relaxed move CSV implementation should use <code>best effort</code> approach.
   * <p>
   * Default value for strict mode flag is false.
   * 
   * @param strict flag for strict mode.
   * @return this pointer.
   */
  CsvFormat strict(boolean strict);

  /**
   * Get value of <code>strict mode</code> flag. Default value is false.
   * 
   * @return value of <code>strict mode</code> flag.
   * @see #strict(boolean)
   */
  boolean strict();
}
