package js.template;

import java.io.File;
import java.io.IOException;

/**
 * Template engine is a factory that creates template instances. Template engine implementation may have optional properties and
 * developer should consult implementation API for supported properties.
 * <p>
 * Recommend way to deploy template engine is Java service. It is also recommended to cache and reuse template engine but is
 * legal to have multiple instances with different properties. For this reason template engine implementation should be reusable
 * and thread safe.
 * 
 * <pre>
 * TemplateEngine engine = ServiceLoader.load(TemplateEngine.clas).iterator().next();
 * engine.setProperty("js.template.serialize.operator", true);
 * ...
 * Template template = engine.getTemplate(new File("store.htm"));
 * ...
 * List&lt;Product&gt; products = dao.getFeaturedProducts();
 * template.serialize(httpResponse.getOutputStream(), products);
 * </pre>
 * <p>
 * Template engine does not manage templates repository; it creates template instance based on given template file. It is user
 * code responsibility to provide the right format. Developer should know template implementation syntax.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface TemplateEngine {
	/**
	 * Set engine global properties, inherited by all template instances created by this engine. Engine properties has instance
	 * scope and different engine instances can have different property values.
	 * <p>
	 * Accepted properties set is implementation specific. Is acceptable for implementation to have no properties at all, in
	 * which case this method does nothing. User code developer should study specific implementation documentation.
	 * 
	 * @param name property name,
	 * @param value property value.
	 */
	void setProperty(String name, Object value);

	/**
	 * Get template instance based on given template file. Template engine implementation should ensure returned template
	 * instance is reusable and thread safe. Also template instance should inherit all properties from creator template engine.
	 * 
	 * @param templateFile template file.
	 * @return template instance.
	 * @throws IOException if template file not found or initialization fails.
	 */
	Template getTemplate(File templateFile) throws IOException;
}
