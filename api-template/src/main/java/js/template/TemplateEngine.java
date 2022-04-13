package js.template;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * Template engine is a factory that creates template instances. Template engine implementation may have optional
 * properties and developer should consult implementation API for supported ones.
 * <p>
 * Recommend way to deploy template engine is Java service. To avoid templates source parsing that could be costly, it
 * is recommended to cache and reuse template engine. Anyway, is legal to create multiple instances, perhaps with
 * different properties. For this reason template engine implementation should be reusable and thread safe.
 * 
 * <pre>
 * TemplateEngine engine = ServiceLoader.load(TemplateEngine.clas).iterator().next();
 * engine.setProperty("js.template.serialize.operator", true);
 * ...
 * Template template = engine.getTemplate(new File("store.htm"));
 * ...
 * List&lt;Product&gt; products = dao.getFeaturedProducts();
 * template.serialize(products, httpResponse.getWriter());
 * </pre>
 * <p>
 * Template engine does not manage templates repository; it creates template instance based on given template source. It
 * is developer responsibility to provide the right format. Also developer should know template implementation syntax.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface TemplateEngine
{
  /**
   * Set engine global properties, inherited by all template instances created by this engine. Engine properties has
   * instance scope and different engine instances can have different property values.
   * <p>
   * Accepted properties set is implementation specific. Is acceptable for implementation to have no properties at all,
   * in which case this method does nothing. Developer should study specific implementation documentation.
   * 
   * @param name property name,
   * @param value property value.
   */
  void setProperty(String name, Object value);

  /**
   * Get template instance based on given template source reader. Template engine implementation should ensure returned
   * template instance is reusable and thread safe. Also template instance should inherit all properties from creator
   * template engine.
   * <p>
   * Caller should provide a name of the source document. This name is used by implementation to cache and reuse
   * template instance and should be unique per JVM.
   * <p>
   * To simplify API integration, implementation is requested to close given source reader.
   * 
   * @param templateName unique name for template source document,
   * @param reader reader for template source.
   * @return template instance.
   * @throws IOException if read operation fails.
   */
  Template getTemplate(String templateName, Reader reader) throws IOException;

  /**
   * Convenient variant of {@link #getTemplate(String, Reader)} when template source is a file. Also this method uses
   * {@link File#getAbsolutePath()} as unique template name.
   * 
   * @param file template source file.
   * @return template instance.
   * @throws IOException if template file not found or read operation fails.
   */
  Template getTemplate(File file) throws IOException;
}
