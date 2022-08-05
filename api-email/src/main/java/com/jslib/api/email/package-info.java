/**
 * Simple API for email sending. This API deals only with email sending and is designed for simple tasks like user
 * registration. To send an email, one should get email instance for desired template, set email instance properties and
 * send it, see sample code below. Sender implementation should manage a templates repository where every template has a
 * unique name; developer needs to know template name in order to create email instance. Recommended way is to use base
 * name of the template file. In sample code below, template name <code>user-registration</code> is derived from file
 * name existing into templates repository.
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
 * For a complete list of supported properties see {@link com.jslib.api.email.EmailSender} class description. Here is a sample
 * usage; sender instance is retrieved and configured then reused for email creation.
 * 
 * <pre>
 * EmailSender sender = ServiceLoader.load(EmailSender.clas).iterator().next();
 * ConfigBuilder builder = new ConfigBuilder(new File(&quot;conf/emails.xml&quot;));
 * sender.config(builder.build());
 * ...
 * sender.getEmail("user-registration").subject("user registration").to(account.getEmailAddress()).send(account);
 * </pre>
 * <p>
 * Email sender supports also locale sensitive email. Templates repository implementation can be locale sensitive and
 * store different templates per locale settings, specifically language. If this is the case, uses
 * {@link com.jslib.api.email.EmailSender#getEmail(Locale, String)} to retrieve email instance.
 * 
 * <h3 id="email-object">Email Object</h3>
 * <p>
 * An email object has content and all information needed for delivery, aka properties. Email content is generated from
 * a template and {@link com.jslib.api.email.Email} class has setters for properties. Optionally email can contain attached files,
 * see {@link com.jslib.api.email.Email#file(File...)}.
 * <p>
 * Template format depends on used templates engine. Is developer responsibility to match used template engine and
 * template files format. Rest of this description focuses on X(HT)ML templates engine.
 * <p>
 * Basically template describe email content and has operators to inject dynamic content. It is a HTML file with only
 * <code>body</code> element.
 * 
 * <pre>
 *  &lt;html&gt;
 *      &lt;body&gt;
 *          &lt;h3&gt;Dear &lt;span data-text="name"&gt;&lt;/span&gt;,&lt;/h3&gt;
 *          &lt;p&gt;Your account name is &lt;span data-text="account"&gt;&lt;/span&gt;.&lt;/h3&gt;
 *          . . .
 *      &lt;/body&gt;
 *  &lt;/html&gt;
 * </pre>
 * 
 * Without entering into details, above template has two variables: <code>name</code> and <code>account</code>. Email
 * send method, see {@link com.jslib.api.email.Email#send(Object...)} accepts a user object and inject instance fields into
 * template variables then send resulting email content.
 * 
 * <pre>
 * class User
 * {
 *   private String name;
 *   private String account;
 *   private String emailAddr;
 * }
 * </pre>
 * <p>
 * For a full description of email instance properties see {@link com.jslib.api.email.Email}. If need to set many email properties,
 * like long list CC and BCC it is convenient to use email model base class, see {@link com.jslib.api.email.EmailModel}.
 * 
 * <pre>
 * class UserEmail extends EmailModel
 * {
 *   public UserEmail(User user)
 *   {
 *     super(user);
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
 * 
 *   public String cc()
 *   {
 *     return "list of comma separated email addresses";
 *   }
 * }
 * . . .
 * sender.getEmail("user-registration").send(new UserEmail(user));
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
package com.jslib.api.email;

import java.io.File;
import java.util.Locale;
