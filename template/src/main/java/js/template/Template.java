package js.template;

import java.io.IOException;
import java.io.Writer;

/**
 * Template instance is created by templates engine and perform domain object injection. Implementation should be
 * reusable and thread safe. Also should be able to cope with not restricted complex domain model and after processing
 * model instance should not be changed.
 * <p>
 * In order to not alter domain model with interfaces specialized only for presentation, templates engine recommend
 * using reflection. If use object fields or getter methods is implementation detail. Anyway, using fields access comes
 * with two advantages:
 * <ol>
 * <li>domain model interface should satisfy business logic needs only; it is not advisable to add getters not strictly
 * requested by business,
 * <li>when using getters developer may be tempted to manipulate model fields in order to fill presentation needs
 * resulting in impedance mismatch.
 * </ol>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Template
{
  /**
   * Set template instance properties overriding the template engine ones.
   * <p>
   * Accepted properties set is implementation specific. Is acceptable for implementation to have no properties at all,
   * in which case this method does nothing. User code developer should study specific implementation documentation.
   * 
   * @param name property name,
   * @param value property value.
   */
  void setProperty(String name, Object value);

  /**
   * Inject domain model into template and serialize to given writer. Implementation should be idempotent. Calling this
   * method multiple times on the same template instance and with the same model object should render the same result.
   * Also model state should not be changed.
   * <p>
   * Though does not make much sense null domain model is accepted. If domain model is null implementation should
   * serialize template itself.
   * 
   * @param writer writer to send template processing result,
   * @param model domain object, null accepted.
   * @throws IllegalArgumentException if <code>writer</code> argument is null.
   * @throws IOException if template serialization fails.
   */
  void serialize(Object model, Writer writer) throws IOException;

  /**
   * Inject domain object into template and return resulting document as string. Implementation should be idempotent.
   * Calling this method multiple times on the same template instance and with the same model object should render the
   * same result. Also model state should not be changed.
   * <p>
   * For conformance with {@link #serialize(Object, Writer)} null domain object is accepted. If domain model is null
   * implementation should return template itself converted to string.
   * 
   * @param model domain object, null accepted.
   * @return resulting document serialized as string.
   */
  String serialize(Object model);
}
