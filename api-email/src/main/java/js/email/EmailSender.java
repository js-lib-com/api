package js.email;

import java.util.Locale;

import js.lang.Config;
import js.lang.ConfigException;

/**
 * Simple sender for template based email. To send an email, one should get email instance for desired template, set
 * email instance properties and send it, see sample code. Sender implementation should manage a templates repository
 * where every template has a unique name; developer needs to know template name in order to create email instance.
 * Recommended way is to use base name of the template file. In sample code below, template name
 * <code>user-registration</code> is derived from file name existing into templates repository.
 * <p>
 * Before using, sender instance should be configured. At a minimum, repository path should be declared so that
 * implementation to be able to locate templates. Configuration object could be loaded from a XML file with content like
 * next sample. Note that configuration step is mandatory; if skip it sender instance is not valid and cannot be used.
 * 
 * <pre>
 * &lt;emails&gt;
 *  &lt;property name="js.repository.path" value="/opt/tomcat/webapps/fax2mail/email/" /&gt;
 *  &lt;property name="js.files.pattern" value="*.htm" /&gt;
 *  &lt;property name="js.template.engine" value="js.template.xhtml.XhtmlTemplateEngine" /&gt;
 * &lt;/emails&gt;
 * </pre>
 * <p>
 * For a complete list of supported properties see next section. Here is a sample usage; sender instance is retrieved
 * and configured then reused for email creation.
 * 
 * <pre>
 * EmailSender sender = ServiceLoader.load(EmailSender.class).iterator().next();
 * ConfigBuilder builder = new ConfigBuilder(new File(&quot;conf/emails.xml&quot;));
 * sender.config(builder.build());
 * ...
 * sender.getEmail("user-registration").subject("user registration").to(account.getEmailAddress()).send(account);
 * </pre>
 * <p>
 * Email sender supports also locale sensitive email. Templates repository implementation can be locale sensitive and
 * store different templates per locale settings, specifically language. If this is the case, uses
 * {@link #getEmail(Locale, String)} to retrieve email instance.
 * 
 * <h3>Configuration Properties</h3>
 * <p>
 * Here are all supported properties by current sender version. Implementation specific properties could also be part of
 * the same configuration object, for example <code>mail.smtp.host</code> for JavaMail host name.
 * <p>
 * Some properties are used as default value when related email instance property is not provided. For example, if email
 * instance has not <code>from</code> address - see {@link Email#from(String)} sender uses its
 * <code>js.from.address</code> value. Anyway, email instance properties take precedence.
 * <table>
 * <tr>
 * <td>js.dev.mode
 * <td>Development mode. In development mode email sender does not actually send email messages to email server but just
 * dump email message content to standard out.
 * <tr>
 * <td>js.repository.path
 * <td>Templates repository path. This property is mandatory and is the absolute path to repository directory. If
 * multi-locale is enabled repository path contains <code>${locale}</code> variable, placeholder for locale code, e.g.
 * <code>/opt/tomcat/webapps/fax2mail/${locale}/email/</code>. Locale code is implementation specific but usually is
 * language tag as used by {@link Locale#toLanguageTag()} and {@link Locale#forLanguageTag(String)}.
 * <tr>
 * <td>js.files.pattern
 * <td>Pattern for files from templates repository. Should have format as accepted by WildcardFilter, specifically uses
 * the characters '?' and '*' to represent a single or multiple wildcard characters, e.g. <code>*.html</code>. This
 * property is optional; if not provided use {@link #DEF_FILE_PATTERN}.
 * <tr>
 * <td>js.template.engine
 * <td>Class for template engine instance, optional. If this value is missing sender should load template engine
 * implementation from JVM services.
 * <tr>
 * <td>js.content.type
 * <td>Sender content type used when email content type is missing, see {@link Email#contentType(String)}. Optional
 * value for sender global content type, default to {@link #DEF_CONTENT_TYPE}. If both email and sender content type
 * property is missing mentioned default value is used.
 * <tr>
 * <td>js.email.subject
 * <td>Sender email subject. This optional property value is used when all email processed by a sender has the same
 * subject, e.g. newsletters. Anyway, email instance subject takes precedence, if present - see
 * {@link Email#subject(String)}.
 * <tr>
 * <td>js.from.address
 * <td>Value for <code>from</code> address, common to all email sent by this sender instance. Sender uses this value is
 * email instance <code>from</code> address is not configured - see {@link Email#from(String)}.
 * <tr>
 * <td>js.envelope.from.address
 * <td>Value for <code>envelope from</code> address, common to all email sent by this sender instance. Sender uses this
 * value is email instance <code>envelope from</code> address is not configured - see
 * {@link Email#envelopeFrom(String)}.
 * <tr>
 * <td>js.reply.to.addresses
 * <td>Comma separated <code>reply to</code> addresses, used to receive email replies. This property is common to all
 * email sent by this sender instance. Anyway, email instance property takes precedence, if present - see
 * {@link Email#replyTo(String...)}.
 * </table>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface EmailSender
{
  /**
   * Property key for development mode. In development mode email sender does not actually send email messages to email
   * server but just dump email message content to standard out.
   */
  static final String PROP_DEV_MODE = "js.dev.mode";
  /**
   * Property key for templates repository path. This property is mandatory and is the absolute path to repository
   * directory. If multi-locale is enabled repository path contains <code>${locale}</code> variable, placeholder for
   * locale code, e.g. <code>/opt/tomcat/webapps/fax2mail/${locale}/email/</code>. Locale code is implementation
   * specific but usually is language tag as used by {@link Locale#toLanguageTag()} and
   * {@link Locale#forLanguageTag(String)}.
   */
  static final String PROP_REPOSITORY_PATH = "js.repository.path";
  /**
   * Property key for files pattern from templates repository. Should have format as accepted by WildcardFilter,
   * specifically uses the characters '?' and '*' to represent a single or multiple wildcard characters, e.g.
   * <code>*.html</code>. This property is optional; if not provided use {@link #DEF_FILE_PATTERN}.
   */
  static final String PROP_FILE_PATTERN = "js.files.pattern";
  /**
   * Property key for template engine instance, optional. If this value is missing sender should load template engine
   * implementation from JVM services.
   */
  static final String PROP_TEMPLATE_ENGINE = "js.template.engine";
  /**
   * Property key for sender content type. Optional value for sender global content type, default to
   * <code>text/html; charset=UTF-8</code>.
   */
  static final String PROP_CONTENT_TYPE = "js.content.type";
  /**
   * Property key for sender email subject. This optional property value is used when all email processed by a sender
   * has the same subject, e.g. newsletters.
   */
  static final String PROP_EMAIL_SUBJECT = "js.email.subject";
  /** Property key for <code>from</code> address, common to all email sent by this sender instance. */
  static final String PROP_FROM_ADDRESS = "js.from.address";
  /** Property key for <code>envelope from</code> address, common to all email sent by this sender instance. */
  static final String PROP_ENVELOPE_FROM_ADDRESS = "js.envelope.from.address";
  /**
   * Property key for comma separated <code>reply to</code> addresses, used to receive email replies. This property is
   * common to all email sent by this sender instance.
   */
  static final String PROP_REPLY_TO_ADDRESSES = "js.reply.to.addresses";

  /**
   * Default files pattern for templates repository. This value is used if {@link #PROP_FILE_PATTERN} property is not
   * provided.
   */
  static final String DEF_FILE_PATTERN = "*.htm";

  /** Default content type. This value is used if {@link EmailSender#PROP_CONTENT_TYPE} property is not provided. */
  static final String DEF_CONTENT_TYPE = "text/html; charset=UTF-8";

  /**
   * Configure this configurable using a given configuration object. Configuration object is clearly dependent on this
   * configurable needs; it is configurable responsibility to ensure configuration object is valid, accordingly its internal
   * rules. If validation fails implementation should throw {@link ConfigException}.
   * <p>
   * Any exception that may occur into configuration process are bubbled up.
   * 
   * @param config configuration object.
   * @throws ConfigException if given configuration object is not valid.
   * @throws Exception if anything goes wrong on configuration process.
   */
  void config(Config config) throws Exception;

  /**
   * Get email instance based on named template. Email sender should be configured with email templates repository. If
   * templates repository is locale sensitive this method uses system default locale. This method is a factory method.
   * It is implementation decision if create a new email instance or reuse.
   * 
   * @param templateName template name.
   * @return email instance.
   * @throws IllegalArgumentException if template name is null or empty.
   * @throws EmailException if template is not found or is invalid.
   */
  Email getEmail(String templateName);

  /**
   * Get email instance based on named template for requested locale settings. This method is locale sensitive variant
   * of {@link #getEmail(String)}. Used locale code should match repository path property -
   * {@link #PROP_REPOSITORY_PATH} and is implementation specific; recommend way is to use language tag as accepted by
   * {@link Locale#toLanguageTag()} and {@link Locale#forLanguageTag(String)}.
   * 
   * @param locale locale settings,
   * @param templateName template name.
   * @return email instance.
   * @throws IllegalArgumentException if <code>locale</code> argument is null or <code>template</code> argument is null
   *           or empty.
   * @throws EmailException if template is not found or is invalid.
   */
  Email getEmail(Locale locale, String templateName);

  /**
   * Convenient way to send ad hoc emails. This method is devised for simple usage but comes with couple limitations:
   * <ul>
   * <li>it does not support multiple destinations, copy carbon and blind copy carbon destinations,
   * <li>envelope from and reply to addresses are always set to <code>from</code>,
   * <li>email addresses personal information is not used,
   * <li>idRight field from message ID is always this library signature,
   * <li>content type is always text/html with UTF-8.
   * </ul>
   * 
   * @param from sender email address,
   * @param to destination email address,
   * @param subject email subject,
   * @param content email content.
   */
  void send(String from, String to, String subject, String content);
}
