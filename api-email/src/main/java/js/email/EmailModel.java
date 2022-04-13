package js.email;

/**
 * Base class for user defined email models. Email model wraps both dynamic content and email instance properties.
 * <p>
 * Uses this class if need to set many email properties. This is meant to avoid invoking multiple email property
 * setters.
 * 
 * <pre>
 * class AccountEmail extends EmailModel
 * {
 *   public AccountEmail(Account account)
 *   {
 *     super(account);
 *   }
 * 
 *   public String subject()
 *   {
 *     return "user registration";
 *   }
 * 
 *   public String to()
 *   {
 *     return user.getEmailAddress();
 *   }
 *   ...
 * }
 * ...
 * sender.getEmail("user-registration").send(new AccountEmail(account));
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public abstract class EmailModel
{
  /** Wrapped domain model to inject into template. */
  private final Object model;

  /**
   * Create email model instance.
   * 
   * @param model domain model used to resolve template variables.
   */
  protected EmailModel(Object model)
  {
    this.model = model;
  }

  /**
   * Get email subject.
   * 
   * @return email subject.
   */
  public abstract String subject();

  /**
   * Get email destination addresses. This getter returns one or more email addresses used as email destination. Uses
   * comma separator is more.
   * 
   * @return comma separated list of email destination addresses.
   */
  public abstract String to();

  /**
   * Get email addresses for copy carbon destination. This getter returns one or more email addresses, comma separated
   * if more.
   * 
   * @return comma separated list of email destination addresses.
   */
  public String cc()
  {
    return null;
  }

  /**
   * Get email addresses for blind copy carbon destination. This getter returns one or more email addresses, comma
   * separated if more.
   * 
   * @return comma separated list of email destination addresses.
   */
  public String bcc()
  {
    return null;
  }

  /**
   * Get sender address.
   * 
   * @return sender address.
   */
  public String from()
  {
    return null;
  }

  /**
   * Get envelope from address used by reporting server to send bounce message.
   * 
   * @return envelope from address.
   */
  public String envelopeFrom()
  {
    return null;
  }

  /**
   * Get list of email addresses used to receive this email reply.
   * 
   * @return comma separated list of email addresses.
   */
  public String replyTo()
  {
    return null;
  }

  /**
   * Get email content type.
   * 
   * @return email content type.
   */
  public String contentType()
  {
    return null;
  }

  /**
   * Get wrapped domain model to inject into email template.
   * 
   * @return domain model.
   */
  public Object model()
  {
    return model;
  }
}
