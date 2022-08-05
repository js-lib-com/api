package com.jslib.api.dom;

/**
 * A child node is either an element or text content. This interface is used in conjunction with {@link Element#getChildNodes()}
 * to iterate over both child elements and text nodes. Due to this package document structure text is mixed with elements only
 * on formatted text.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface ChildNode {
	/**
	 * Test if this child node is a document element.
	 * 
	 * @return true if this child node is an element.
	 */
	boolean isElement();

	/**
	 * Test if this child node is text content.
	 * 
	 * @return true if this child node is text content.
	 */
	boolean isText();

	/**
	 * Cast this child node to element.
	 * 
	 * @return element represented by this child node.
	 */
	Element asElement();

	/**
	 * Cast this child node to text content.
	 * 
	 * @return text content represented by this child node.
	 */
	String asText();
}
