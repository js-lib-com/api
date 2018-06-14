package js.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Facade for JSON parser and serializer. This facade supplies methods to {@link #stringify(Writer, Object)} and
 * {@link #parse(Reader, Type)} values to and from JSON character streams. There are also convenient methods for values to JSON
 * strings conversion, see {@link #stringify(Object)} and {@link #parse(String, Type)}.
 * <p>
 * Parsing of not homogeneous arrays is supported but caller should supplies the type of every array item, see
 * {@link #parse(Reader, Type[])}.
 * <p>
 * JSON facade also provides methods for (de)serialization with inband type information, see
 * {@link #stringifyObject(Writer, Object)} and {@link #parseObject(Reader)}. This is an extension to JSON protocol that include
 * <code>class</code> property before the actual object JSON stream.
 * <p>
 * A JSON stream with inbad type information may look like below. Please note <code>class</code> property from JSON start.
 * 
 * <pre>
 *  {"class":"js.net.Event","id":1234 ... }
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Json {
	/**
	 * Serialize value to JSON character stream and left it unclosed. Both primitive and aggregated values are allowed. If value
	 * is not primitive all fields are scanned reflectively, less static, transient and synthetic. If a field is aggregated on
	 * its turn traverse its fields too. This allows for not restricted fields graph. Note that there is no guarantee regarding
	 * fields order inside parent object.
	 * <p>
	 * If <code>value</code> argument is null serialize JSON <code>null</code> keyword.
	 * <p>
	 * After serialization completes <code>writer</code> is flushed but left unclosed.
	 * 
	 * @param writer character stream to write value on,
	 * @param value value to serialize, null accepted.
	 * @throws IOException if IO write operation fails.
	 */
	void stringify(Writer writer, Object value) throws IOException;

	/**
	 * Deserialize value of expected type. After parsing completion used <code>reader</code> remains opened.
	 * <p>
	 * This method uses auto cast in order to simplify user code but is caller responsibility to ensure requested
	 * <code>type</code> is cast compatible with type of variable to assign to.
	 * <p>
	 * This JSON parser method is relaxed and follows a best effort approach. If a named property from JSON stream does not have
	 * the same name field into target object, parser just warn on log and ignore. Also, fields from target object with no
	 * values into JSON stream are set to null.
	 * 
	 * @param reader character stream to read from,
	 * @param type expected type.
	 * @param <T> type to auto cast on return, cast compatible with <code>type</code> argument.
	 * @return instance of expected type initialized from JSON character stream.
	 * @throws IOException if read operation fails.
	 * @throws JsonException if parsing process fails perhaps due to syntax violation on input.
	 * @throws ClassCastException if given <code>type</code> cannot cast to expected type variable <code>T</code>.
	 */
	<T> T parse(Reader reader, Type type) throws IOException, JsonException, ClassCastException;

	/**
	 * Deserialize array of mixed types. This method is able to parse not homogeneous arrays; if in need for homogeneous array
	 * one could use {@link #parse(Reader, Type)} with <code>type</code> set to desired array class. Every object from JSON
	 * stream array must have the type defined by <code>types</code> parameter. It is caller responsibility to ensure that JSON
	 * stream array types number and order match requested <code>types</code>. Return empty object array if <code>types</code>
	 * is empty.
	 * <p>
	 * This method uses the same best effort approach as {@link #parse(Reader, Type)}. Note that after parsing completion used
	 * <code>reader</code> remains opened.
	 * 
	 * @param reader character stream to read from,
	 * @param types expected types, empty array accepted.
	 * @return newly created and initialized array, possible empty.
	 * @throws IOException if IO read operation fails.
	 * @throws JsonException if parsing process fails perhaps due to syntax violation on input.
	 */
	Object[] parse(Reader reader, Type[] types) throws IOException, JsonException;

	/**
	 * Serialize object instance with inband type information. An object serialized with this method must be parsed by
	 * {@link #parseObject(Reader)} companion method. After serialization completes <code>writer</code> is flushed but left
	 * unclosed.
	 * <p>
	 * Implementation note: this method uses a proprietary extension to JSON protocol.
	 * 
	 * @param writer characters stream writer,
	 * @param value object instance to serialize.
	 * @throws IOException if write operation fails.
	 */
	void stringifyObject(Writer writer, Object value) throws IOException;

	/**
	 * Deserialize JSON encoded object with inband type information. This method expect a stream generated by
	 * {@link #stringifyObject(Writer, Object)} companion method. After parsing completion used <code>reader</code> remains
	 * opened.
	 * <p>
	 * Implementation note: this method uses a proprietary extension to JSON protocol.
	 * 
	 * @param reader characters stream reader.
	 * @param <T> object type.
	 * @return initialized instance auto-cast to expected parameterized type <code>T</code>.
	 * @throws IOException if read operation fails.
	 * @throws JsonException if parsing process fails perhaps due to syntax violation on input.
	 * @throws ClassCastException if inband type cannot be cast to expected parameterized type <code>T</code>.
	 */
	<T> T parseObject(Reader reader) throws IOException, JsonException, ClassCastException;

	/**
	 * Handy method for value object serialization to JSON formatted string.
	 * 
	 * @param value primitive or aggregate value.
	 * @return value JSON string representation.
	 */
	String stringify(Object value);

	/**
	 * Parse JSON encoded value and return instance of requested type. This method creates an instance of <code>type</code> and
	 * initialize its fields from JSON string. It uses auto cast in order to simplify user code but is caller responsibility to
	 * ensure requested <code>type</code> is cast compatible with type of variable to assign to.
	 * <p>
	 * This method uses the same best effort approach as {@link #parse(Reader, Type)}.
	 * 
	 * @param value JSON encode value,
	 * @param type desired value type.
	 * @param <T> type to auto cast on return, cast compatible with <code>type</code> argument.
	 * @return newly created instance.
	 * @throws JsonException if given string value is not valid JSON format.
	 */
	<T> T parse(String value, Type type) throws JsonException;
}
