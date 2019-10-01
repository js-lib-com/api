package js.dom;

import java.io.IOException;
import java.io.Writer;

/**
 * Document interface is a simplified morph of W3C DOM Document. Basically a document is a tree of elements with a unique root.
 * It supplies getter for root element and methods for element creation, import and searching.
 * <p>
 * All search operations are performed using depth-first algorithm, i.e starts from root and explores as far as possible along
 * each branch before backtracking. There are basically two kinds of search: <code>getBy</code> and <code>findBy</code>. First
 * always returns an {@link Element} or null while the second returns {@link EList}, possible empty. One can use
 * {@link EList#isEmpty} to check if <code>findBy</code> actually found something.
 * <p>
 * Where reasonable, search methods has variant for name space. As stated not all search methods have support for name space;
 * for example searching by CSS class has not name space since <code>class</code> attribute name is only in global scope. All
 * methods handling name spaces follows W3C DOM convention, i.e. has the name suffixed with <code>NS</code>. First parameter is
 * always name space. If name space parameter is null, name space aware methods degenerate to their not name space counterpart,
 * e.g. <code>getByTagNS(null, "p")</code> delegates <code>getByTag("p")</code>.
 * <p>
 * If document is parsed without name space support, element 'NS' getters always returns null and 'NS' finders always return
 * empty list. Also, on document without name space support, element and attribute names include prefix. For example,
 * <code>ns:name</code> element has null name space and local name 'ns:name'. When searching for element use 'ns:name' for name
 * and search method without <code>NS</code> suffix.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Document {
	/**
	 * Test if this document is a XML instance.
	 * 
	 * @return true if this document is XML.
	 */
	boolean isXML();

	/**
	 * Create element and set attributes. Create an element of requested tag owned by this document. Also set attributes values
	 * if optional attribute name/value pairs are present; throws illegal argument if a name/value is incomplete, i.e. odd
	 * number of arguments. It is user code responsibility to supply attribute name and value in proper order. Note that newly
	 * created element is not part of document tree until explicitly add or insert it as a child to a parent. So, elements
	 * creation follows the same W3C DOM pattern: create the element then add it as a child.
	 * 
	 * <pre>
	 * Element p = doc.createElement(&quot;p&quot;, &quot;id&quot;, &quot;paragraph-id&quot;, &quot;title&quot;, &quot;tooltip description&quot;);
	 * body.addChild(p);
	 * </pre>
	 * 
	 * @param tagName tag name for element to be created,
	 * @param attrNameValues optional pairs of attribute name followed by value.
	 * @return newly created element.
	 * @throws IllegalArgumentException if <code>tagName</code> parameter is null or empty or <code>attrNameValues</code>
	 *             parameter is not complete.
	 * @see Element#setAttr(String, String)
	 */
	Element createElement(String tagName, String... attrNameValues);

	/**
	 * Create element in requested name space. Create an element of requested tag, owned by this document, within the name
	 * space. Note that newly created element is not part of document tree until explicitly add or insert it as a child to a
	 * parent. So, elements creation follows the same W3C DOM pattern: create the element then add it as a child.
	 * 
	 * <pre>
	 * Element p = doc.createElementNS(namespaceURI, &quot;p&quot;);
	 * body.addChild(p);
	 * </pre>
	 * <p>
	 * Null <code>namespaceURI</code> is considered global scope and this method delegates
	 * {@link #createElement(String, String...)}, without attributes.
	 * <p>
	 * This method has no attributes parameters as {@link #createElement(String, String...)} because is user code decision if
	 * attribute name has name space or not.
	 * 
	 * @param namespaceURI name space URI,
	 * @param tagName tag name for element to be created.
	 * @return newly created element.
	 * @throws IllegalArgumentException if <code>tagName</code> parameter is null or empty.
	 */
	Element createElementNS(String namespaceURI, String tagName);

	/**
	 * Import element. Import an element that belongs to another document and return it. Note that newly imported element is not
	 * part of this document tree until explicitly appended or inserted to a parent element. Also import is always deep, that
	 * is, element children are imported too.
	 * 
	 * <pre>
	 * Element el = doc.importElement(foreignDoc.getByTag(&quot;Luke Skywalker&quot;));
	 * doc.getByTag(&quot;Dark Vader&quot;).addChild(el);
	 * </pre>
	 * 
	 * @param el foreign element.
	 * @return newly imported element.
	 * @throws IllegalArgumentException if element parameter is null or if element already belongs to this document.
	 */
	Element importElement(Element el);

	/**
	 * Retrieve the root of this document tree.
	 * 
	 * @return this document root.
	 */
	Element getRoot();

	/**
	 * Get the element with specified ID. This method looks for an attribute with type ID, usually named <code>id</code>.
	 * Attribute type is set at document validation using DTD or schema information. Trying to use this method on a document
	 * without schema always returns null.
	 * 
	 * @param id element ID to look for.
	 * @return element with specified ID or null.
	 * @throws IllegalArgumentException if <code>id</code> parameter is null or empty.
	 */
	Element getById(String id);

	/**
	 * Search entire document for elements with given tag name and return the first element found. Returns null if there is no
	 * element with requested tag name. Note that wild card asterisk (*) matches all tags in which case first child is returned.
	 * <p>
	 * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
	 * consider tags name as case sensitive. Also, if document contains name spaces but is parsed without name space support,
	 * tag name for name space elements should use name space prefix.
	 * 
	 * @param tagName case sensitive tag name to search for.
	 * @return first element with specified tag or null.
	 * @throws IllegalArgumentException if tag name parameter is null or empty.
	 */
	Element getByTag(String tagName);

	/**
	 * Get the element with tag name in the requested name space. Tag name is a local name, that is, has no name space prefix;
	 * name space if identified by separated <code>namespaceURI</code> parameter.
	 * <p>
	 * Search entire document for elements with given tag name, within name space, and return the first found. Returns null if
	 * there is no element with requested tag name. Note that wild card asterisk (*) for tag name matches all tags in which case
	 * first child is returned. Always returns null if this document is not parsed with name space support.
	 * <p>
	 * If name space parameter is wild card ('*') all name spaces are considered, that is, entire document. Null name space is
	 * considered default scope, that is, elements without any prefix in which case this method degenerates to
	 * {@link #getByTag(String)} counterpart.
	 * <p>
	 * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
	 * consider tag names as case sensitive.
	 * 
	 * @param namespaceURI name space URI,
	 * @param tagName local tag name to search for, case sensitive.
	 * @return first element with specified tag or null.
	 * @throws IllegalArgumentException if tag name parameter is null or empty.
	 */
	Element getByTagNS(String namespaceURI, String tagName);

	/**
	 * Find elements by tag. Return all elements from this document having specified tag name. Returns empty list if there is
	 * not element with requested tag name. Note that wild card asterisk (*) for tag name matches all tags in which case all
	 * elements are returned.
	 * <p>
	 * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
	 * consider tags name as case sensitive. If document contains name spaces but is parsed without name space support, tag name
	 * for name space elements should use name space prefix.
	 * 
	 * @param tagName tag name to search for, case sensitive.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if <code>tagName</code> parameter is null or empty.
	 */
	EList findByTag(String tagName);

	/**
	 * Find elements having requested tag name into the given name space. Returns empty list if there is not element with
	 * requested tag name. Note that wild card asterisk (*) for tag name matches all tags in which case all elements are
	 * returned.
	 * <p>
	 * If name space parameter is wild card ('*') all name spaces are considered, that is, entire document. Null name space is
	 * considered default scope, that is, elements without any prefix.
	 * <p>
	 * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
	 * consider tag names as case sensitive.
	 * 
	 * @param namespaceURI name space URI, possible wild card or null,
	 * @param tagName local tag name, case sensitive.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if <code>tagName</code> parameter is null or empty.
	 */
	EList findByTagNS(String namespaceURI, String tagName);

	/**
	 * Get element by XPath. Evaluate XPath expression and return first element found. Returns null if XPath evaluation has no
	 * results. Note that XPath expression is case sensitive; this is especially relevant for HTML documents, that uses upper
	 * case for tag names.
	 * <p>
	 * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
	 * <code>args</code> arguments should be supplied.
	 * 
	 * @param xpath XPath expression to evaluate,
	 * @param args optional arguments if <code>xpath</code> is formatted.
	 * @return first element found or null.
	 * @throws IllegalArgumentException if <code>xpath</code> parameter is null or empty.
	 */
	Element getByXPath(String xpath, Object... args);

	/**
	 * Evaluate XPath expression with name space prefixes and return first element found. Returns null if XPath evaluation has
	 * no results. Note that XPath expression is case sensitive; this is especially relevant for HTML documents that uses upper
	 * case for tag names.
	 * <p>
	 * Name space context maps prefixes to name space URI. See {@link NamespaceContext} for a discussion about expressions with
	 * name space.
	 * <p>
	 * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
	 * <code>args</code> arguments should be supplied.
	 * 
	 * @param namespaceContext name space context maps prefixes to name space URI,
	 * @param xpath XPath expression to evaluate,
	 * @param args optional arguments if <code>xpath</code> is formatted.
	 * @return first element found or null.
	 * @throws IllegalArgumentException if <code>namespaceContext</code> parameter is null or <code>xpath</code> parameter is
	 *             null or empty.
	 */
	Element getByXPathNS(NamespaceContext namespaceContext, String xpath, Object... args);

	/**
	 * Evaluate XPath expression and return the list of found elements. Returns empty list if XPath evaluation has no results.
	 * Note that XPath expression is case sensitive; this is especially relevant for HTML documents that uses upper case for tag
	 * names.
	 * <p>
	 * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
	 * <code>args</code> arguments should be supplied.
	 * 
	 * @param xpath XPath expression to evaluate,
	 * @param args optional arguments if <code>xpath</code> is formatted.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if <code>xpath</code> parameter is null or empty.
	 */
	EList findByXPath(String xpath, Object... args);

	/**
	 * Evaluate XPath expression with name space prefixes and return the list of found elements. Returns empty list if XPath
	 * evaluation has no results. Note that XPath expression is case sensitive; this is especially relevant for HTML documents
	 * that uses upper case for tag names.
	 * <p>
	 * Name space context maps prefixes to name space URI. See {@link NamespaceContext} for a discussion about expressions with
	 * name space.
	 * <p>
	 * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
	 * <code>args</code> arguments should be supplied.
	 * 
	 * @param namespaceContext name space context,
	 * @param xpath XPath expression to evaluate,
	 * @param args optional arguments if <code>xpath</code> is formatted.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if <code>namespaceContext</code> parameter is null or <code>xpath</code> parameter is
	 *             null or empty.
	 */
	EList findByXPathNS(NamespaceContext namespaceContext, String xpath, Object... args);

	/**
	 * Evaluate CSS selectors and return first element found. Returns null if CSS evaluation has no results. CSS
	 * <code>selector</code> can be formatted as supported by {@link String#format} in which case <code>args</code> parameter
	 * should be supplied.
	 * <p>
	 * Implementation note: this method is not yet implemented and always throws unsupported operation.
	 * 
	 * @param selector CSS selector to evaluate,
	 * @param args optional arguments if <code>selectors</code> is formatted.
	 * @return first found element or null.
	 * @throws IllegalArgumentException if <code>selector</code> parameter is null or empty.
	 */
	Element getByCss(String selector, Object... args);

	/**
	 * Find elements by CSS selectors. Evaluate CSS selectors and return found elements. Returns empty list if CSS evaluation
	 * has no results. CSS <code>selector</code> can be formatted as supported by {@link String#format} in which case
	 * <code>args</code> parameter should be supplied.
	 * <p>
	 * Implementation note: this method is not yet implemented and always throws unsupported operation.
	 * 
	 * @param selector CSS selector to evaluate,
	 * @param args optional arguments if <code>selector</code> is formatted.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if <code>selector</code> parameter is null or empty.
	 */
	EList findByCss(String selector, Object... args);

	/**
	 * Get element by CSS class. Retrieve first element possessing requested CSS class. Returns null if there is no element with
	 * such CSS class.
	 * 
	 * @param cssClass CSS class to search for.
	 * @return found element or null.
	 * @throws IllegalArgumentException if CSS class parameter is null or empty.
	 */
	Element getByCssClass(String cssClass);

	/**
	 * Find elements by CSS class. Retrieve elements possessing given CSS class. Returns empty list if given CSS class is null
	 * or empty or there is no element with such CSS class.
	 * 
	 * @param cssClass CSS class to search for.
	 * @return list of found elements, possible empty.
	 * @throws IllegalArgumentException if CSS class parameter is null or empty.
	 */
	EList findByCssClass(String cssClass);

	/**
	 * Get element by attribute name and optional value. Retrieve first element possessing requested attribute. Returns null if
	 * there is no element with requested attribute name and value, if present.
	 * 
	 * @param name attribute name,
	 * @param value optional attribute value.
	 * @return found element or null.
	 * @throws IllegalArgumentException if attribute name parameter is null or empty.
	 */
	Element getByAttr(String name, String... value);

	/**
	 * Get the list of document elements having requested attribute. Requested attribute is identified by its name and optional
	 * by its value. Returns empty list if there is no element with requested attribute.
	 * 
	 * @param name attribute name,
	 * @param value optional attribute value.
	 * @return found element or null.
	 * @throws IllegalArgumentException if attribute name parameter is null or empty.
	 */
	EList findByAttr(String name, String... value);

	/**
	 * Serialize this document to standard output.
	 */
	void dump();

	/**
	 * Serialize this document and optionally close destination writer. Serialize this document to given writer. If optional
	 * <code>closeWriter</code> flag is present and is true closes destination writer after serialization completes.
	 * 
	 * @param writer destination writer,
	 * @param closeWriter optional flag, if true close destination writer.
	 * @throws IOException if writing operation fails.
	 */
	void serialize(Writer writer, boolean... closeWriter) throws IOException;
}
