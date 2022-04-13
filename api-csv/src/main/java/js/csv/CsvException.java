package js.csv;

/**
 * Unchecked CSV engine exception thrown when CSV implementation fail to do its job. Is recommended that this exception
 * to be thrown only when CSV implementation is working in strict mode - see {@link CsvFormat#strict()}.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public class CsvException extends RuntimeException
{
  /** Java serialization version. */
  private static final long serialVersionUID = 3767217155714395093L;

  /**
   * Create CSV exception with formatted message. See {@link String#format(String, Object...)} for supported formatting
   * tags.
   * 
   * @param message formatted exception message,
   * @param args optional arguments if message contains formatting tags.
   */
  public CsvException(String message, Object... args)
  {
    super(String.format(message, args));
  }

  /**
   * Create CSV exception of given cause.
   * 
   * @param cause exception root cause.
   */
  public CsvException(Throwable cause)
  {
    super(cause);
  }
}
