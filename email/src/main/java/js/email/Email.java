package js.email;

import java.io.File;

/**
 * Template based email. An email object has content and all information needed for delivery - lets name these pieces of
 * information fields. Optionally email can contain attached files, see {@link #file(File...)}.
 * <p>
 * Email object is created by email sender, see {@link EmailSender#getEmail(String)}. Emails are all based on a
 * {@link js.template.Template} and support dynamic content injection, allowing for email customization for every single
 * delivery. An email template has a name known on application scope that is used by email sender to create email
 * instance.
 * 
 * <pre>
 * EmailSender sender = ServiceLoader.load(EmailSender.clas).iterator().next();
 * ...
 * sender.getEmail("user-registration").to(account.getEmailAddress()).send(account);
 * </pre>
 * 
 * <p>
 * In sample code, template name is <code>user-registration</code>. This name is used by email sender to locate template
 * file into templates repository. Template describe email content and has operators to inject dynamic content. It is a
 * HTML file with both <code>head</code> and <code>body</code>. Head is excluded from resulting email and contains meta
 * data needed for send, like <code>from</code> and <code>subject</code>; content type is also configured via template
 * meta, but usually is UTF-8 which is the default value. Here is template sample using X(HT)ML templates reference
 * implementation.
 * 
 * <pre>
 *  &lt;html&gt;
 *      &lt;head data-exclude="true"&gt;
 *          &lt;meta name="from" content="customers@cloud.ro" /&gt;
 *          &lt;meta name="subject" content="cloud registration" /&gt;
 *          &lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /&gt;
 *      &lt;/head&gt;
 *      &lt;body&gt;
 *          &lt;h3&gt;Dear &lt;span data-text="name"&gt;&lt;/span&gt;,&lt;/h3&gt;
 *          &lt;p&gt;Your account name is &lt;span data-text="account"&gt;&lt;/span&gt;.&lt;/h3&gt;
 *          . . .
 *      &lt;/body&gt;
 *  &lt;/html&gt;
 * </pre>
 * 
 * When create email instance, parses template head meta elements and initialize email instance fields. A meta element
 * is standard HTML meta tag and has <code>name</code> and <code>content</code> attributes bound to email instance field
 * name, respective value. Uses comma as separator if value should have multiple items. Here is the list of supported
 * fields:
 * <ul>
 * <li>{@link js.email.Email#from(String) from} - originator email address,
 * <li>{@link js.email.Email#envelopeFrom(String) envelopeFrom} - this is reverse path used by remote agent to send back
 * bounce message,
 * <li>{@link js.email.Email#to(String...) to} - list of email addresses for destinations,
 * <li>{@link js.email.Email#cc(String...) cc} - list of email addresses for copy carbon destinations,
 * <li>{@link js.email.Email#bcc(String...) bcc} - list of email addresses for blind copy carbon destinations,
 * <li>{@link js.email.Email#subject(String) subject} - email subject,
 * <li>{@link js.email.Email#replyTo(String...) replyTo} - a list of email addresses where email reply should be sent.
 * </ul>
 * 
 * <p>
 * This interface is designed for method chaining. It gave-up with Java Bean setter convention and uses shorter, direct
 * names. This way is possible to send and email in a single line of code:
 * 
 * <pre>
 * email.from(&quot;from@mail.com&quot;).to(&quot;to@another.mail.com&quot;).cc(&quot;cc@yet.another.mail.com&quot;).send(content);
 * </pre>
 * <p>
 * And because immutable fields, that is, fields that are common to a group of emails and not changing for every email,
 * are stored into template is possible to have really short email sending code. Most common it is reduced to setting
 * <em>to</em> and <em>send</em>.
 * 
 * <pre>
 * email.to(user.getEmailAddr()).send(user);
 * </pre>
 * 
 * <p>
 * Finally, email properties can be set in bulk using {@link #set(EmailProperties)}. It is a programmatic alternative to
 * template header meta. It is especially handy when group of email properties repeats for related emails. Email
 * properties can be cached and reused.
 * 
 * <pre>
 * EmailProperties properties = new EmailProperies();
 * properties.setSubject(&quot;Meeting with sales on Friday&quot;);
 * properties.setFrom(&quot;sales@compnay.com&quot;);
 * properties.setReplyTo(&quot;sales@company.com&quot;);
 * properties.setCc(&quot;sales.head@company.com&quot;);
 * ...
 * sender.getEmail(&quot;meeting-invitation&quot;).set(properties).to(user.getEmailAddress()).send(user);
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Email
{
  /**
   * Set email properties. This method is designed to set email instance properties in bulk. It is an alternative to
   * properties set from template header meta.
   * 
   * @param properties email properties.
   * @return this pointer.
   * @throws IllegalArgumentException if <code>properties</code> argument or is null.
   * @throws EmailException if any address from email properties argument is not valid.
   * @see EmailProperties
   */
  Email set(EmailProperties properties);

  /**
   * Set sender email address. Given address syntax should be accepted by email service provider.
   * 
   * @param address sender email address.
   * @return this pointer.
   * @throws IllegalArgumentException if <code>address</code> parameter is null or empty.
   * @throws EmailException if <code>address</code> parameter is not valid.
   */
  Email from(String address);

  /**
   * Set envelope address used by reporting server to send bounce message. Note that this address should not have
   * personal data. Given address syntax should be accepted by email service provider.
   * 
   * @param address envelope from address.
   * @return this pointer.
   * @throws IllegalArgumentException if <code>address</code> parameter is null or empty.
   * @throws EmailException if <code>address</code> parameter is not valid, specifically if has personal data.
   */
  Email envelopeFrom(String address);

  /**
   * Set list of email addresses used to receive this email reply, if any. Given addresses syntax should be acceptable
   * for parsing by email service provider.
   * 
   * @param addresses reply addresses.
   * @return this pointer.
   * @throws IllegalArgumentException if no <code>address</code> provided or an <code>address</code> parameter is null
   *           or empty.
   * @throws EmailException if an <code>address</code> parameter is not valid.
   */
  Email replyTo(String... addresses);

  /**
   * Set list of addresses for this email destination. Given addresses syntax should be acceptable for parsing by email
   * service provider.
   * 
   * @param addresses destination email addresses.
   * @return this pointer.
   * @throws IllegalArgumentException if no <code>address</code> provided or an <code>address</code> parameter is null
   *           or empty.
   * @throws EmailException if an <code>address</code> parameter is not valid.
   */
  Email to(String... addresses);

  /**
   * Set list of email addresses for copy carbon destination. Given addresses syntax should be acceptable for parsing by
   * email service provider.
   * 
   * @param addresses addresses for copy carbon destination.
   * @return this pointer.
   * @throws IllegalArgumentException if no <code>address</code> provided or an <code>address</code> parameter is null
   *           or empty.
   * @throws EmailException if an <code>address</code> parameter is not valid.
   */
  Email cc(String... addresses);

  /**
   * Set list of email addresses for blind copy carbon destination. Given addresses syntax should be acceptable for
   * parsing by email service provider.
   * 
   * @param addresses addresses for blind copy carbon destination.
   * @return this pointer.
   * @throws IllegalArgumentException if no <code>address</code> provided or an <code>address</code> parameter is null
   *           or empty.
   * @throws EmailException if an <code>address</code> parameter is not valid.
   */
  Email bcc(String... addresses);

  /**
   * Set this email subject.
   * 
   * @param subject email subject.
   * @return this pointer.
   */
  Email subject(String subject);

  /**
   * Set attached files.
   * 
   * @param files files to attach to this email.
   * @return this pointer.
   */
  Email file(File... files);

  /**
   * Inject dynamic content into this email template and send it. Given <code>object</code> should have fields with the
   * name corresponding to named variables from template. It is implementation detail if template engine is strict or
   * relaxed regarding unresolved template variables.
   * 
   * <pre>
   * class User {
   * 	private String name;
   * 	private String account;
   * 	...
   * }
   * email.send(user);
   * </pre>
   * 
   * <p>
   * It is also possible to send static emails, in which case <code>object</code> parameter is missing. Template for a
   * static email has no variables; it is developer responsibility to match static template with missing object
   * argument. If template has variables and object is missing, variables are sent unresolved.
   * 
   * <p>
   * Optionally, this method takes care to initialize email instance fields: searches <code>object</code> for fields
   * with the same name as email fields, types should be identical, and update email instance. For example, if
   * <code>object</code> has an array of addresses named <code>to</code> set email instance field to that field value.
   * 
   * <pre>
   * Person person = {
   * 	// address to inject into email instance field
   * 	private InternetAddress[] to;
   * 	...
   * 	// object specific fields to inject into template variables
   * }
   * ...
   * email.send(person);
   * </pre>
   * 
   * Note that email instance fields injection from <code>object</code> argument takes precedence over template head
   * meta and email instance setter.
   * 
   * @param object optional dynamic content.
   * @throws EmailException if anything goes wrong on template injection or sending process.
   */
  void send(Object... object);
}
