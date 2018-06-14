package js.dom;

/**
 * An element attribute is a name / value tuple, both strings.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Attr {
	/**
	 * Get attribute name. Implementation should guarantee that returned string is not null and is trimmed.
	 * 
	 * @return attribute name.
	 */
	String getName();

	/**
	 * Get attribute value. Implementation should guarantee that returned string is not null and is trimmed.
	 * 
	 * @return attribute value.
	 */
	String getValue();
}
