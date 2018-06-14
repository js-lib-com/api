/**
 * Sender for Templates Based Email Messages. This API deals only with email sending and is
 * designed for simple tasks like user registration. To send email, user code should get email instance
 * for desired template and just send it. Sender manages a templates repository where every template 
 * has a unique name; user space code needs to know template name in order to create email instance. 
 * Recommended way is to use file base name as template name.
 * <p>
 * In next sample code, get email based on <code>user-registration</code> template, inject account properties
 * and send. Templates repository contains a template file <code>user-registration.htm</code>.
 * <pre>
 * EmailSender sender = ServiceLoader.load(EmailSender.clas).iterator().next();
 * ...
 * sender.getEmail("user-registration").to(account.getEmailAddress()).send(account);
 * </pre>
 * <p>
 * In order to manage templates repository, sender implementation is configured via {@link js.email.EmailSender#config(js.lang.Config)}.
 * Configuration object could be loaded from an XML with content like next sample, see {@link js.lang.Config} description.
 * <pre>
 * &lt;emails&gt;
 * 	&lt;property name="js.repository.path" value="/opt/tomcat/webapps/fax2mail/email/" /&gt;
 * 	&lt;property name="js.files.pattern" value="*.htm" /&gt;
 * 	&lt;property name="js.template.engine" value="js.template.xhtml.XhtmlTemplateEngine" /&gt;
 * 	&lt;property name="js.bounce.domain" value="kids-cademy.com" /&gt;
 * 	&lt;property name="mail.transport.protocol" value="smtp" /&gt;
 * 	&lt;property name="mail.debug" value="true" /&gt;
 * &lt;/emails&gt;
 * </pre>
 * Property <code>js.repository.path</code> is mandatory and is the absolute path to repository directory.
 * If multi-language is enabled repository path contains <code>${language}</code> variable, that is a
 * placeholder for 2-letter language code, e.g. <code>/opt/tomcat/webapps/fax2mail/${language}/email/</code>.
 * Optional <code>js.files.pattern</code> acts as filter for files list operation. If files pattern is 
 * missing uses all files from repository. 
 * <p>
 * Property <code>js.template.engine</code> is also optional; if missing email sender uses templates 
 * provider found on runtime context. Rest of properties are implementation specific, in this case 
 * JavaMail. User code developer should review implementation API for specific properties description. 
 * 
 * <h3 id="email-object">Email Object</h3> 
 * Email object contains the content and all information needed for delivery and is based on a HTML 
 * template. Now, template format depends on used templates engine. Is developer responsibility to match 
 * used template engine and template files format. Rest of this description focuses on X(HT)ML templates engine.
 * <p>
 * Basically template describe email content and has operators to inject dynamic content. It is a HTML 
 * file with both <code>head</code> and <code>body</code>. Head is excluded from resulting email and 
 * contains meta data needed for send, like <code>from</code> and <code>subject</code>; content type 
 * is also configured via template meta, but usually is UTF-8, which is the default value. Head is optional; 
 * in case is missing user code should use setters or object injection to initialize email instance 
 * subject and addresses. 
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
 * Without entering into details, above template has two variables: <code>name</code> and <code>account</code>.
 * Email send method, see {@link js.email.Email#send(Object...)} accepts a user object and inject instance
 * fields into template variables, excludes header and send resulting email content. 
 * <pre>
 *  class User {
 *  	private String name;
 *  	private String account;
 *  	private String emailAddr;
 *  }
 *  
 *  EmailSender sender = Factory.getInstance(EmailSender.class);
 *  ...
 *  sender.getEmail("user-registration").to(user.getEmailAddr()).send(user);
 * </pre>
 *  Note that email instance is not reusable; create a new instance for every delivery.
 * 
 * <h3>Email Instance Fields</h3> 
 * An email instance has internal fields that need to be initialized before sending; is quite common 
 * to have related emails that differ on couple fields, perhaps on destination address only. To go 
 * simple, common fields are stored into email template and only those specific are set programmatically. 
 * Both template head meta and field setters have the same name; here is the list of supported fields: 
 * <ul>
 * <li>{@link js.email.Email#from(String) from} - originator email address,
 * <li>{@link js.email.Email#envelopeFrom(String) envelopeFrom} - this is reverse path used by remote agent to send back bounce message,
 * <li>{@link js.email.Email#to(String...) to} - list of email addresses for destinations,
 * <li>{@link js.email.Email#cc(String...) cc} - list of email addresses for copy carbon destinations,
 * <li>{@link js.email.Email#bcc(String...) bcc} - list of email addresses for blind copy carbon destinations,
 * <li>{@link js.email.Email#subject(String) subject} - email subject,
 * <li>{@link js.email.Email#replyTo(String...) replyTo} - a list of email addresses where email reply should be sent.
 * </ul>
 * If <code>envelopeFrom</code> and <code>replyTo</code> are missing <code>from</code> address is used instead.
 * <p>
 * There is a third method to initialize email fields: set email properties in bulk using {@link js.email.Email#set(EmailProperties)}. 
 * It is a programmatic alternative to template header meta. It is especially handy when group of email 
 * properties repeats for related emails. Email properties can be cached and reused.
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
 * <h3>Bounce Back</h3>
 * Envelope address is used for sending back notification about delivery failing. This package support 
 * a slightly changed version of VERP algorithm, enabled if <code>js.bounce.domain</code> property is 
 * present into configuration object, see {@link js.email.EmailSender#config(js.lang.Config)}. Email 
 * MessageID is encode Base64 and used as local part for <code>envelopeFrom</code> address; for domain
 * part of <code>envelopeFrom</code> uses configured bounce domain. If bounce back, delivery failing 
 * notification address is exactly <code>envelopeFrom</code> we set on email sent. Just extract local 
 * part an decode to get MesssageID for failing email. 
 * <p>
 * If bounce domain is configured <code>envelopeFrom</code> address is forced by above algorithm and 
 * ignore address value specified by any means: template header meta, object injection or instance setter.
 *  
 * @author Iulian Rotaru
 * @version final
 */
package js.email;

