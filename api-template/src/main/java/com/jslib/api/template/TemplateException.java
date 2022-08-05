package com.jslib.api.template;

/**
 * Fatal exception thrown when templates engines is not able to fulfill its job.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public class TemplateException extends RuntimeException
{
  /** Java serialization version. */
  private static final long serialVersionUID = 4275248735997310488L;

  /**
   * Construct exception with formatted message. See {@link String#format(String, Object...)} for message format
   * description.
   * 
   * @param message formatted message,
   * @param args optional format arguments.
   */
  public TemplateException(String message, Object... args)
  {
    super(String.format(message, args));
  }
}
