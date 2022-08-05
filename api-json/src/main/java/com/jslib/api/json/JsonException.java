package com.jslib.api.json;

/**
 * Not checked exception thrown when JSON parsing or serialization fails for some reasons.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public class JsonException extends RuntimeException
{
  /** Java serialization version. */
  private static final long serialVersionUID = 8175755582232053470L;

  /**
   * Create parser exception with formatted message. See {@link String#format(String, Object...)} for supported
   * formatting tags.
   * 
   * @param message exception formatted message,
   * @param args optional formatted arguments.
   */
  public JsonException(String message, Object... args)
  {
    super(String.format(message, args));
  }

  /**
   * Create parser exception with error reporter and root cause throwable.
   * 
   * @param cause root cause throwable.
   */
  public JsonException(Throwable cause)
  {
    super(cause);
  }
}
