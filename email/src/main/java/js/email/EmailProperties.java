package js.email;

/**
 * Email instance properties. Email properties are a programmatic alternative to template head meta. It is especially handy when
 * group of email properties repeats for related emails. Email properties can be cached and reused.
 * <p>
 * In sample code creates common properties and reuse them for every delivery.
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
 * @version draft
 */
public class EmailProperties {
	/** Sender email address. */
	private String from;
	/** Envelope address used by reporting server to send bounce message. */
	private String envelopeFrom;
	/** List of email addresses used to receive email reply. */
	private String[] replyTo;
	/** List of addresses for email destination. */
	private String[] to;
	/** List of email addresses for copy carbon destination. */
	private String[] cc;
	/** List of email addresses for blind copy carbon destination. */
	private String[] bcc;
	/** Email subject. */
	private String subject;

	public boolean hasFrom() {
		return from != null;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean hasEnvelopeFrom() {
		return envelopeFrom != null;
	}

	public String getEnvelopeFrom() {
		return envelopeFrom;
	}

	public void setEnvelopeFrom(String envelopeFrom) {
		this.envelopeFrom = envelopeFrom;
	}

	public boolean hasReplyTo() {
		return replyTo != null;
	}

	public String[] getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String[] replyTo) {
		this.replyTo = replyTo;
	}

	public boolean hasTo() {
		return to != null;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public boolean hasCc() {
		return cc != null;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public boolean hasBcc() {
		return bcc != null;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public boolean hasSubject() {
		return subject != null;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
