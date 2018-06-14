package js.email;

import java.util.Locale;

import js.lang.Configurable;

/**
 * Simple sender for template based emails. To send email, user code should get email instance for desired template and just
 * send it, see sample code. Sender implementation should manage a templates repository where every template has a unique name;
 * user space code needs to know template name in order to create email instance. Recommended way is to use file base name as
 * template name.
 * <p>
 * Template name <code>user-registration</code> is derived from file name existing into templates repository.
 * 
 * <pre>
 * EmailSender sender = ServiceLoader.load(EmailSender.clas).iterator().next();
 * ...
 * sender.getEmail("user-registration").to(account.getEmailAddress()).send(account);
 * </pre>
 * <p>
 * Email sender supports also locale sensitive emails. Templates repository implementation can be locale sensitive and store
 * different templates per locale settings, specifically language. If this is the case, uses {@link #getEmail(Locale, String)}
 * to retrieve email instance.
 * 
 * <p>
 * In order to manage templates repository, sender implementation is configured via {@link #config(js.lang.Config)}.
 * Configuration object could be loaded from an XML with content like next sample.
 * 
 * <pre>
 * &lt;emails&gt;
 * 	&lt;property name="js.repository.path" value="/opt/tomcat/webapps/fax2mail/email/" /&gt;
 * 	&lt;property name="js.files.pattern" value="*.htm" /&gt;
 * 	&lt;property name="js.template.engine" value="js.template.xhtml.XhtmlTemplateEngine" /&gt;
 * 	&lt;property name="js.bounce.domain" value="kids-cademy.com" /&gt;
 * &lt;/emails&gt;
 * </pre>
 * 
 * Property <code>js.repository.path</code> is mandatory and is the absolute path to repository directory. If multi-language is
 * enabled repository path contains <code>${language}</code> variable, that is a placeholder for 2-letter language code, e.g.
 * <code>/opt/tomcat/webapps/fax2mail/${language}/email/</code>. Optional <code>js.files.pattern</code> acts as filter for files
 * list operation. If files pattern is missing uses all files from repository.
 * <p>
 * Properties <code>js.template.engine</code> and <code>js.bounce.domain</code> are optional. If template engine is missing,
 * email sender uses templates provider found on runtime context. Bounce domain is used for bounce back messages, see package
 * description.
 * <p>
 * Implementation specific properties could also be part of the same configuration object, for example
 * <code>mail.smtp.host</code> for JavaMail host name. Email sender implementation should support warm reconfiguration. It is
 * implementation detail what components from sender are reconfigured but it is expected that templates engine and repository to
 * be configured only at sender instance creation. Reconfiguration process should be thread safe.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface EmailSender extends Configurable {
	/**
	 * Set default <code>from</code> address used when email instance has none specified. Provided email address should be valid
	 * as described by RFC822. Typical address syntax is of the form "user@host.domain" or "Personal Name &lt;user@host.domain&gt;".
	 * 
	 * @param fromAddress default <code>from</code> address.
	 * @throws IllegalArgumentException if <code>fromAddress</code> argument is null, empty or invalid.
	 */
	void setFromAddress(String fromAddress);

	/**
	 * Get email instance based on named template. Email sender should be configured with email templates repository. If
	 * templates repository is locale sensitive this method uses system default locale.
	 * <p>
	 * This method is a factory method. It is implementation decision if create a new email instance or reuse. When create new
	 * instance implementation should initialize instance state from template meta elements, see <a
	 * href="package-summary.html#email-object">package description</a>.
	 * 
	 * @param templateName template name.
	 * @return email instance.
	 * @throws IllegalArgumentException if template name is null or empty.
	 * @throws EmailException if template is not found or is invalid.
	 */
	Email getEmail(String templateName);

	/**
	 * Get email instance based on named template for requested locale settings. This method is locale sensitive variant of
	 * {@link #getEmail(String)}.
	 * 
	 * @param locale locale settings based on language two letters, ISO 639-1 code,
	 * @param templateName template name.
	 * @return email instance.
	 * @throws IllegalArgumentException if <code>locale</code> argument is null or <code>template</code> argument is null or
	 *             empty.
	 * @throws EmailException if template is not found or is invalid.
	 */
	Email getEmail(Locale locale, String templateName);

	/**
	 * Convenient way to send ad hoc emails. This method is devised for simple usage but comes with couple limitations: it does
	 * not support multiple destinations, copy carbon and blind copy carbon destinations, envelope from and reply to addresses
	 * are always set to <code>from</code>, email addresses personal information is not used, idRight field from message ID is
	 * always this library signature and content type is always text/html with UTF-8.
	 * 
	 * @param from sender email address,
	 * @param to destination email address,
	 * @param subject email subject,
	 * @param content email content.
	 */
	void send(String from, String to, String subject, String content);
}
