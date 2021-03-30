package js.dom;

/**
 * Document element. This interface is the central piece of the document model; in fact a document is just a tree of
 * elements. An element has a tag, optional attributes and possible other elements as children. Also an element may have
 * text content but is not possible to have both child elements and text. Anyway, there is support for text with HTML
 * formatting tags, see {@link #setRichText(String)} and its related getter.
 * <p>
 * An element always belongs to an owner document that is the document that created it, see
 * {@link Document#createElement(String, String...)}. Anyway, even if element has owner document it does necessarily
 * means is part of document elements tree. It should be explicitly added to a parent element using
 * {@link #addChild(Element...)}.
 * <p>
 * Once part of the document tree, an element has a mandatory parent element - except root element that has null parent,
 * see {@link #getParent()}. Element parent and children enables document tree navigation.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Element
{
  /**
   * Retrieve this element owner document.
   * 
   * @return this element owner document.
   */
  Document getDocument();

  /**
   * Add child element(s). Add one or more child element(s) to this element children list end. Supports a variable
   * number of elements as arguments so is possible to add more than just one child in a single call. Child (children)
   * to be added must belong to the same {@link Document} as this element. If not, this method performs
   * {@link Document#importElement} first; note that import is always deep.
   * <p>
   * If a child to be added is already part of the document tree this method does not create a new element but simple
   * moves it to the newly position.
   * 
   * @param child one or more child element(s) to be appended.
   * @return this element.
   * @throws IllegalArgumentException if a child is null.
   */
  Element addChild(Element... child);

  /**
   * Replace this element with replacement. Replacement must belong to the same {@link Document} as this. If not, this
   * method performs {@link Document#importElement} first; note that import is always deep.
   * <p>
   * After replacement this element is not longer part of the document tree.
   * 
   * @param replacement element to replace this one.
   * @throws IllegalArgumentException if replacement element parameter is null.
   */
  void replace(Element replacement);

  /**
   * Replace an existing child element with a new element. If replacement element does not belong to this element
   * document, this method performs deep import.
   * <p>
   * After replacement existing element is not longer part of the document tree.
   * 
   * @param replacement element to be inserted,
   * @param existing existing child, to be replaced.
   * @return this object.
   * @throws IllegalArgumentException if replacement or existing element parameter is null.
   */
  Element replaceChild(Element replacement, Element existing);

  /**
   * Insert child element as first child of this element. Perform {@link Document#importElement(Element)} if child
   * element does not belong to this element document.
   * <p>
   * If child to be inserted is already part of the document tree this method just move it as first child of this
   * element.
   * 
   * @param child element to be inserted as first child.
   * @return this object.
   * @throws IllegalArgumentException if <code>child</code> parameter is null.
   */
  Element insertChild(Element child);

  /**
   * Insert child elements as first children of this element. Elements list to be inserted should be part of a document;
   * there is no way to create elements list instance outside a document. There are two cases here:
   * <ol>
   * <li>elements list belongs to the same document: elements are simple moved to this element first elements,
   * <li>foreign elements list: perform {@link Document#importElement(Element)} before actual insert.
   * </ol>
   * 
   * @param children elements to be inserted as first child elements.
   * @return this object.
   * @throws IllegalArgumentException if <code>children</code> parameter is null.
   */
  Element insertChildren(EList children);

  /**
   * Insert element before this one, that is, inserted element becomes previous sibling. If inserted element already
   * exist in owning document it is first removed. If not, this method performs {@link Document#importElement} first;
   * note that import is always deep.
   * 
   * @param sibling element to be inserted.
   * @return this element.
   * @throws IllegalArgumentException if <code>sibling</code> parameter is null.
   * @throws IllegalStateException if this node has no parent.
   */
  Element insertBefore(Element sibling);

  /**
   * Clone this element. If deep flag is true clone this element descendants too, otherwise a shallow copy is performed.
   * <p>
   * Cloned element belongs to the same document but has no parent, that is, is not part of document tree and cannot be
   * searched for. It should be explicitly added to a parent.
   * 
   * @param deep optional deep cloning flag, default to false.
   * @return clone element.
   */
  Element clone(boolean deep);

  /**
   * Self-remove. This method get element parent and remove itself from parent children list. After execution of this
   * method element instance become invalid and attempting to use it may render not specified results.
   */
  void remove();

  /**
   * Remove all this element children. Note that this method remove all nodes, not only child elements.
   * 
   * @return this element.
   */
  Element removeChildren();

  /**
   * Get first descendant element identified by formatted XPath expression. Returns null if XPath expression does not
   * identify any element. Evaluation context is this element but only if XPath expression is not absolute or document
   * global, that is, does not start with single or double slash. If this is the case, returned element is not
   * necessarily descendant of this element.
   * <p>
   * XPath expression is case sensitive; this is especially relevant for HTML documents, that uses upper case for tag
   * names. Also XPath expression can be formatted as supported by {@link String#format} in which case <code>args</code>
   * parameter should be supplied.
   * 
   * @param xpath formatted XPath expression,
   * @param args optional arguments if expression contains formats.
   * @return found element or null.
   * @throws IllegalArgumentException if <code>xpath</code> parameter is null or empty.
   */
  Element getByXPath(String xpath, Object... args);

  /**
   * Evaluate XPath expression with name space prefixes and return first element found. Returns null if XPath evaluation
   * has no results. Evaluation context is this element but only if XPath expression is not absolute or document global,
   * that is, does not start with single or double slash. If this is the case, returned element is not necessarily
   * descendant of this element.
   * <p>
   * Name space context maps prefixes to name space URIs. See {@link NamespaceContext} for a discussion about
   * expressions with name space.
   * <p>
   * XPath expression is case sensitive; this is especially relevant for HTML documents that uses upper case for tag
   * names. Also XPath expression can be formatted as supported by {@link String#format} in which case <code>args</code>
   * parameter should be supplied.
   * 
   * @param namespaceContext name space context maps prefixes to name space URIs,
   * @param xpath XPath expression to evaluate,
   * @param args optional arguments if <code>xpath</code> is formatted.
   * @return first element found or null.
   * @throws IllegalArgumentException if <code>namespaceContext</code> parameter is null or <code>xpath</code> parameter
   *           is null or empty.
   */
  Element getByXPathNS(NamespaceContext namespaceContext, String xpath, Object... args);

  /**
   * Convenient variant of {@link #getByXPathNS(NamespaceContext, String, Object...)} usable when document has a single
   * namespace.
   * <p>
   * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
   * <code>args</code> arguments should be supplied.
   * 
   * @param namespaceURI name space URI, possible wild card or null,
   * @param xpath XPath expression to evaluate,
   * @param args optional arguments if <code>xpath</code> is formatted.
   * @return first element found or null.
   * @throws IllegalArgumentException if <code>namespaceURI</code> or <code>xpath</code> parameter is null or empty.
   * @since 1.2
   */
  Element getByXPathNS(String namespaceURI, String xpath, Object... args);

  /**
   * Evaluate XPath expression and return the list of found elements. Returns empty list if XPath evaluation has no
   * results. Evaluation context is this element but only if XPath expression is not absolute or document global, that
   * is, does not start with single or double slash. If this is the case, returned elements are not necessarily
   * descendants of this element.
   * <p>
   * XPath expression is case sensitive; this is especially relevant for HTML documents that uses upper case for tag
   * names. Also XPath expression can be formatted as supported by {@link String#format} in which case <code>args</code>
   * parameter should be supplied.
   * 
   * @param xpath XPath expression to evaluate,
   * @param args optional arguments if <code>xpath</code> is formatted.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if <code>xpath</code> parameter is null or empty.
   */
  EList findByXPath(String xpath, Object... args);

  /**
   * Evaluate XPath expression with name space prefixes and return the list of found elements. Returns empty list if
   * XPath evaluation has no results. Evaluation context is this element but only if XPath expression is not absolute or
   * document global, that is, does not start with single or double slash. If this is the case, returned elements are
   * not necessarily descendants of this element.
   * <p>
   * XPath expression is case sensitive; this is especially relevant for HTML documents that uses upper case for tag
   * names. Also XPath expression can be formatted as supported by {@link String#format} in which case <code>args</code>
   * parameter should be supplied.
   * <p>
   * Name space context maps prefixes to name space URI. See {@link NamespaceContext} for a discussion about expressions
   * with name space.
   * 
   * @param namespaceContext name space context,
   * @param xpath XPath expression to evaluate,
   * @param args optional arguments if <code>xpath</code> is formatted.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if <code>namespaceContext</code> parameter is null or <code>xpath</code> parameter
   *           is null or empty.
   */
  EList findByXPathNS(NamespaceContext namespaceContext, String xpath, Object... args);

  /**
   * Convenient variant of {@link #findByXPathNS(NamespaceContext, String, Object...)} usable when document has a single
   * namespace.
   * <p>
   * XPath expression <code>xpath</code> can be formatted as supported by {@link String#format} in which case
   * <code>args</code> arguments should be supplied.
   * 
   * @param namespaceURI name space URI, possible wild card or null,
   * @param xpath XPath expression to evaluate,
   * @param args optional arguments if <code>xpath</code> is formatted.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if <code>namespaceURI</code> or <code>xpath</code> parameter is null or empty.
   * @since 1.2
   */
  EList findByXPathNS(String namespaceURI, String xpath, Object... args);

  /**
   * Evaluate CSS selectors and return first element found. Returns null if CSS evaluation has no results. CSS
   * <code>selector</code> can be formatted as supported by {@link String#format} in which case <code>args</code>
   * parameter should be supplied.
   * <p>
   * Implementation note: this method is not yet implemented and always throws unsupported operation.
   * 
   * @param selector CSS selector to evaluate,
   * @param args optional arguments if <code>selector</code> is formatted.
   * @return first found element or null.
   * @throws IllegalArgumentException if <code>selector</code> parameter is null or empty.
   */
  Element getByCss(String selector, Object... args);

  /**
   * Find elements by CSS selectors. Evaluate CSS selectors and return found elements. Returns empty list if CSS
   * evaluation has no results. CSS <code>selector</code> can be formatted as supported by {@link String#format} in
   * which case <code>args</code> parameter should be supplied.
   * <p>
   * Implementation note: this method is not yet implemented and always throws unsupported operation.
   * 
   * @param selectors CSS selectors to evaluate,
   * @param args optional arguments if <code>selector</code> is formatted.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if <code>selector</code> parameter is null or empty.
   */
  EList findByCss(String selectors, Object... args);

  /**
   * Get the first descendant with requested tag name. Search all descendants for elements with given tag name and
   * return the first element found. Returns null if there is no element with requested tag name. Note that wild card
   * asterisk (*) matches all tags in which case first child is returned. Search is performed into document order, that
   * is, depth-first.
   * <p>
   * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
   * consider tags name as case sensitive. Also, if document contains name spaces but is parsed without name space
   * support, tag name for name space elements should use name space prefix.
   * 
   * @param tagName case sensitive tag name to search for.
   * @return first descendant element with specified tag or null.
   * @throws IllegalArgumentException if tag name parameter is null or empty.
   */
  Element getByTag(String tagName);

  /**
   * Get the descendant element with tag name in the requested name space. Tag name is a local name, that is, has no
   * name space prefix; name space if identified by separated <code>namespaceURI</code> parameter.
   * <p>
   * Search all descendants for element with given tag name, within name space, and return the first found. Returns null
   * if there is no element with requested tag name. Note that wild card asterisk (*) for tag name matches all tags in
   * which case first child is returned. Always returns null if this document is not parsed with name space support.
   * Search is performed in depth-first order.
   * <p>
   * If name space parameter is wild card ('*') all name spaces are considered, that is, entire document. Null name
   * space is considered default scope, that is, elements without any prefix in which case this method degenerates to
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
   * Find elements by tag name. Return all descendant elements having specified tag name. Returns empty list if there is
   * not element with requested tag name. Note that wild card asterisk (*) for tag name matches all tags in which case
   * all descendant elements are returned.
   * <p>
   * On XML documents tag name is case sensitive whereas in HTML is not. For consistency sake is recommended to always
   * consider tags name as case sensitive. If document contains name spaces but is parsed without name space support,
   * tag name for name space elements should use name space prefix.
   * 
   * @param tagName tag name to search for, case sensitive.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if tag name parameter is null or empty.
   */
  EList findByTag(String tagName);

  /**
   * Retrieve all descendants with given tag name within requested name space. Search is performed in depth-first order.
   * Returns empty list if there is not element with requested tag name. Note that wild card asterisk (*) for tag name
   * matches all tags in which case all elements are returned.
   * <p>
   * If name space parameter is wild card ('*') all name spaces are considered, that is, entire document. Null name
   * space is considered default scope, that is, elements without any prefix.
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
   * Get first descendant element possessing given CSS class. Returns null if there is no descendant element with
   * requested CSS class.
   * 
   * @param cssClass CSS class.
   * @return descendant element with requested CSS class or null.
   * @throws IllegalArgumentException if CSS class parameter is null or empty.
   */
  Element getByCssClass(String cssClass);

  /**
   * Get all descendant elements possessing requested CSS class. Returns empty list if there is no element with
   * requested CSS class.
   * 
   * @param cssClass CSS class.
   * @return list of found elements, possible empty.
   * @throws IllegalArgumentException if CSS class parameter is null or empty.
   */
  EList findByCssClass(String cssClass);

  /**
   * Get descendant element by attribute name and optional value. Retrieve first descendant element possessing requested
   * attribute. Returns null if there is no element with requested attribute name and value, if present.
   * 
   * @param name attribute name,
   * @param value optional attribute value.
   * @return found element or null.
   * @throws IllegalArgumentException if attribute name parameter is null or empty.
   */
  Element getByAttr(String name, String... value);

  /**
   * Get the list of descendant elements having requested attribute. Requested attribute is identified by its name and
   * optional value. Returns empty list if there is no element with requested attribute.
   * 
   * @param name attribute name,
   * @param value optional attribute value.
   * @return found element or null.
   * @throws IllegalArgumentException if attribute name parameter is null or empty.
   */
  EList findByAttr(String name, String... value);

  // TODO: there is no support from w3c api to find elements by attribute name and / or value
  // trying to create an xpath to emulate is problematic because xpath needs prefix and namecontext uses name space URI
  // find solution or remove method
  // EList findByAttrNS(String namespaceURI, String name, String... value);

  /**
   * Returns this element parent or null if this element is the root.
   * 
   * @return this element parent or null.
   */
  Element getParent();

  /**
   * Get all direct child elements or empty list. Note that this method returns only element nodes and only direct
   * children, not all descendants.
   * 
   * @return this element children or empty list.
   */
  EList getChildren();

  /**
   * Get list of child nodes. Usually an element can have either child elements or text content. In the first case
   * returned list contains all child elements whereas in the second case returns a list with a single text node.
   * Anyway, if this element has rich text, that is, text with HTML formats, returned list contains both text nodes and
   * node elements.
   * 
   * @return list of child nodes.
   */
  Iterable<ChildNode> getChildNodes();

  /**
   * Test if this element has child elements. Note that this method consider only element nodes and only direct
   * children, not all descendants.
   * 
   * @return true if this element has children.
   */
  boolean hasChildren();

  /**
   * Test if this element is empty, that is, it has no child elements nor text content.
   * 
   * @return true if this element is empty.
   */
  boolean isEmpty();

  /**
   * Get first child element or null if this element has no children. Note that this method consider only element nodes
   * and only direct children, not all descendants.
   * 
   * @return this element first child or null.
   */
  Element getFirstChild();

  /**
   * Get last child element or null if this element has no children. Note that this method consider only element nodes
   * and only direct children, not all descendants.
   * 
   * @return this element last child or null.
   */
  Element getLastChild();

  /**
   * Get previous sibling element or null if this element is the first one. Note that this method consider only element
   * nodes.
   * 
   * @return previous sibling or null.
   */
  Element getPreviousSibling();

  /**
   * Get next sibling element or null if this element is the last one. Note that this method consider only element
   * nodes.
   * 
   * @return next sibling or null.
   */
  Element getNextSibling();

  /**
   * Get this element tag name forced to lower case.
   * 
   * @return this element tag name.
   */
  String getTag();

  /**
   * Get case sensitive tag name. Returns tag value as existing into parsed document. For XML documents tag name is case
   * sensitive whereas on XHTML not.
   * 
   * @return this element tag name with preserved case
   */
  String getCaseSensitiveTag();

  /**
   * Set named attribute value. Set the attribute value, creating it if not already present. Empty value is accepted in
   * which case attribute is still created, if not exists, but its value will be empty.
   * 
   * @param name attribute name,
   * @param value attribute value, empty accepted.
   * @return this object.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty or <code>value</code> parameter is
   *           null.
   */
  Element setAttr(String name, String value);

  /**
   * Locate attribute with given local name into requested name space and set its value. Create attribute if not already
   * present. Empty value is accepted in which case attribute is still created, if not exists, but its value will be
   * empty.
   * <p>
   * Null name space is considered default scope, that is, elements without any prefix in which case this method
   * degenerates to {@link #setAttr(String, String)} counterpart.
   * 
   * @param namespaceURI name space URI,
   * @param name attribute local name,
   * @param value attribute value, empty accepted.
   * @return this object.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty or <code>value</code> parameter is
   *           null.
   */
  Element setAttrNS(String namespaceURI, String name, String value);

  /**
   * Set one or more attribute values. This method parameters are variable number of name/value pair for every desired
   * argument. Is caller responsibility to ensure name/value is completely supplied and in the proper order.
   * 
   * <pre>
   * el.setAttr(&quot;id&quot;, &quot;123&quot;, &quot;type&quot;, &quot;text&quot;);
   * </pre>
   * 
   * @param nameValuePairs variable number of attribute name/value pairs.
   * @return this element.
   * @throws IllegalArgumentException if <code>nameValuePairs</code> parameter is not complete, that is, last name has
   *           not value or a value is null.
   */
  Element setAttrs(String... nameValuePairs);

  /**
   * Get attributes list possible empty if this element has no attribute at all.
   * 
   * @return this element attributes list.
   */
  Iterable<Attr> getAttrs();

  /**
   * Get the value of named attribute or null if attribute not found or its value is empty.
   * 
   * @param name attribute name.
   * @return attribute value or null.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty.
   */
  String getAttr(String name);

  /**
   * Locate attribute with local name within given name space and returns its value. Returns null if attribute not found
   * or its value is empty.
   * <p>
   * Null name space is considered default scope, that is, elements without any prefix in which case this method
   * degenerates to {@link #getAttr(String)} counterpart.
   * 
   * @param namespaceURI name space URI,
   * @param name attribute local name,
   * @return attribute value or null.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty.
   */
  String getAttrNS(String namespaceURI, String name);

  /**
   * Remove attribute identified by given name. If no attribute found with specified <code>name</code> this method is
   * NOP.
   * 
   * @param name attribute name.
   * @return this object.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty.
   */
  Element removeAttr(String name);

  /**
   * Remove attribute identified by local name within given name space. If no attribute found with specified
   * <code>name</code> this method is NOP.
   * <p>
   * Null name space is considered default scope, that is, elements without any prefix in which case this method
   * degenerates to {@link #removeAttr(String)} counterpart.
   * 
   * @param namespaceURI name space URI,
   * @param name attribute name.
   * @return this object.
   * @throws IllegalArgumentException if <code>name</code> parameter is null or empty.
   */
  Element removeAttrNS(String namespaceURI, String name);

  /**
   * Test if this element has requested attribute.
   * 
   * @param name attribute name.
   * @return true if this element has named attribute.
   */
  boolean hasAttr(String name);

  /**
   * Test if this element has attribute with local name within given name space.
   * 
   * @param namespaceURI name space URI,
   * @param name attribute local name.
   * @return true if this element has named attribute.
   */
  boolean hasAttrNS(String namespaceURI, String name);

  /**
   * Add given text to this element children list end.
   * 
   * @param text text to add.
   * @return this element.
   */
  Element addText(String text);

  /**
   * Replace this element content with given text. Note that this element children are actually removed.
   * 
   * @param text text to set.
   * @return this element.
   */
  Element setText(String text);

  /**
   * Remove all child text nodes from this element. After this method execution {@link #getText()} returns an empty
   * string. Note that this method operates only on owned text, child elements text is not touched.
   * 
   * @return this element.
   */
  Element removeText();

  /**
   * Get text content for this element and all descendants. Returned string does not contain any markup and no
   * whitespace normalization is performed. If there is no text nodes on this element sub-tree this method returns empty
   * string.
   * 
   * @return element text content, possible empty but never null.
   */
  String getText();

  /**
   * Get text content for this element only. This method is similar to {@link #getText()} but does not include text from
   * descendants, only text nodes direct child of this element.
   * 
   * @return this element text content, possible empty but never null.
   */
  String getTextContent();

  /**
   * Create a document fragment from given rich text and append it to this element, after removing all children. In this
   * method context rich text is a text with format tags like <code>strong</code> or <code>em</code>. Be aware that this
   * method remove all this element children and replace them with given rich text.
   * 
   * @param richText rich text.
   * @return this element.
   */
  Element setRichText(String richText);

  /**
   * Get this element rich text content. In this method context rich text is a text with format tags like
   * <code>strong</code>. This method returns element inner HTML as a string representation. Note that formatting
   * elements are not expected to have attributes and that this method ignores them, if present.
   * 
   * @return this element rich text content.
   */
  String getRichText();

  /**
   * Add CSS class to this element, if not already present. If requested CSS class is already present this method is
   * NOP.
   * 
   * @param cssClass CSS class.
   * @return this element.
   */
  Element addCssClass(String cssClass);

  /**
   * Remove CSS class from this element. If requested CSS class does not exist on this element this method is NOP.
   * 
   * @param cssClass CSS class.
   * @return this element.
   */
  Element removeCssClass(String cssClass);

  /**
   * Toggle given CSS class on this element. If this element already has requested CSS class this method delegates
   * {@link #removeCssClass(String)}, otherwise {@link #addCssClass(String)}.
   * 
   * @param cssClass CSS class.
   * @return this element.
   */
  Element toggleCssClass(String cssClass);

  /**
   * Test if this element has requested CSS class.
   * 
   * @param cssClass CSS class.
   * @return true if this element has requested CSS class.
   */
  boolean hasCssClass(String cssClass);

  /**
   * Return the path through document tree from root to current element. Designed for debugging purposed.
   * 
   * @return this element trace.
   */
  String trace();
}
