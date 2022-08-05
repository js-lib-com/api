/**
 * Simplified X(HT)ML DOM Interface. This package is loosely based on W3C DOM interface, following the 
 * same abstractions of document, element, attribute and so on, but simplified. It is usable to represent
 * in memory both XML and HTML5 documents. There is no support for on stream processing like SAX.
 * <p> 
 * In this package concept a document is a tree of elements and has a root. An element has a tag, optional 
 * attributes and possible other elements as children. Also an element may have text content but is not 
 * possible to have both child elements and text.
 *
 * <p>This package purpose is to hide low level abstractions as node, node type, node list, etc. while focusing
 * on element as a basic construct. Also attribute and text nodes are replaced by simple strings.
 * Finally, document type, processing instruction and comment nodes are not included in this package interface.
 * 
 * <p>
 * Client code should obtain an instance of {@link com.jslib.api.dom.DocumentBuilder} then create or load a document.
 * Once in memory operates on document elements. Document builder can be reused.
 * <pre>
 * DocumentBuilder builder = ServiceLoader.load(DocumentBuilder.class).iterator().next();
 * ...
 * Document document = builder.load(documentFile);
 * // operates on document elements 
 * </pre>
 * 
 * <h3>Known Limitation</h3>
 * <p>This package element abstraction does not have support for mixed child elements and text content. Usually an element 
 * with text content has not child elements. This is by design, in the effort to keep elements navigation simpler. Anyway,
 * there is support for text with formatting tags, see {@link com.jslib.api.dom.Element#setRichText(String)} and its related getter.
 *
 * @author Iulian Rotaru
 * @version final
 */
package com.jslib.api.dom;

