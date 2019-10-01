package js.email;

/**
 * Thrown whenever something goes wrong on email processing.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public class EmailException extends RuntimeException
{
  /** Java serialization version. */
  private static final long serialVersionUID = -3885368112889661842L;

  /**
   * Construct email exception instance with given formatted message. See {@link String#format(String, Object...)} for
   * supported formatting tags.
   * 
   * @param message formatted message,
   * @param args optional arguments used if message is formatted.
   */
  public EmailException(String message, Object... args)
  {
    super(String.format(message, args));
  }

  /**
   * Create email exception instance with given cause.
   * 
   * @param cause exception cause.
   */
  public EmailException(Exception cause)
  {
    super(cause);
  }
}
