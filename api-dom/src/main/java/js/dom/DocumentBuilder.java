package js.dom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;

/**
 * Document object builder. Supply factory methods for documents creation, parsing from string and loading from various
 * sources: file, reader, input stream, input source and URL. There are different factory methods for XML and HTML
 * documents and all are in two flavors: with or without name space support. For name space support this class follows
 * W3C DOM notation convention and uses <code>NS</code> suffix.
 * 
 * @author Iulian Rotaru
 */
public interface DocumentBuilder
{
  EntityResolver getDefaultEntityResolver();

  /**
   * Create empty XML document with requested root element. Created document is not name space aware and uses UTF-8 for
   * character encoding.
   * 
   * @param root tag name of the root element, null or empty not accepted.
   * @return newly created XML document.
   */
  Document createXML(String root);

  /**
   * Create a new XML document with support for name spaces. Created document uses UTF-8 for character encoding.
   * 
   * @param root tag name of the root element, null or empty not accepted.
   * @return newly created document.
   */
  Document createXMLNS(String root);

  // ----------------------------------------------------
  // parse XML document from string source

  /**
   * Parse XML document form source string. Returned document is not name space aware and uses UTF-8 encoding.
   * 
   * @param string non empty source string.
   * @return newly created document.
   * @throws SAXException if source string is not valid XML document.
   */
  Document parseXML(String string) throws SAXException;

  /**
   * Parse XML document from source string with support for namespaces. Returned XML document is namespace aware and
   * uses UTF-8 encoding.
   * 
   * @param string non empty source string.
   * @return newly created document instance.
   * @throws SAXException if source string is not valid XML document.
   */
  Document parseXMLNS(String string) throws SAXException;

  // ----------------------------------------------------
  // load XML document from file

  /**
   * Load XML document from file. Returned document is not namespace aware and uses UTF-8 encoding.
   * 
   * @param file source file.
   * @return newly created XML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid XML document.
   */
  Document loadXML(File file) throws IOException, SAXException;

  /**
   * Load XML document with namespaces support from file. Returned XML document uses UTF-8 encoding.
   * 
   * @param file source file,
   * @return newly created XML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid XML document.
   */
  Document loadXMLNS(File file) throws IOException, SAXException;

  // ----------------------------------------------------
  // load XML document from input stream

  /**
   * Load XML document from input stream. Returned document is not name space aware and uses UTF-8 encoding. Input
   * stream is closed after document load.
   * 
   * @param stream input stream.
   * @return newly created XML document.
   * @throws IOException if input stream reading fails.
   * @throws SAXException if input stream content is not a valid XML document.
   */
  Document loadXML(InputStream stream) throws IOException, SAXException;

  /**
   * Load XML document with name spaces support from input stream. Returned XML document uses UTF-8 encoding. Input
   * stream is closed after document load.
   * 
   * @param stream input stream,
   * @return newly created XML document.
   * @throws IOException if input stream reading fails.
   * @throws SAXException if input stream content is not a valid XML document.
   */
  Document loadXMLNS(InputStream stream) throws IOException, SAXException;

  // ----------------------------------------------------
  // load XML document from reader

  /**
   * Load XML document from reader. Returned document is not name space aware and uses UTF-8 encoding. Source reader is
   * closed after document load.
   * 
   * @param reader source character stream.
   * @return newly created XML document.
   * @throws IOException if character stream reading fails.
   * @throws SAXException if character stream content is not a valid XML document.
   */
  Document loadXML(Reader reader) throws IOException, SAXException;

  /**
   * Load XML document with name spaces support from reader. Returned XML document uses UTF-8 encoding. Source reader is
   * closed after document load.
   * 
   * @param reader source character stream.
   * @return newly created XML document.
   * @throws IOException if character stream reading fails.
   * @throws SAXException if character stream content is not a valid XML document.
   */
  Document loadXMLNS(Reader reader) throws IOException, SAXException;

  // ----------------------------------------------------
  // load XML document from URL

  /**
   * Load XML document from source URL. Returned document is not name space aware and uses UTF-8 encoding.
   * 
   * @param url source document URL.
   * @return newly created XML document.
   * @throws IOException if source document reading fails.
   * @throws SAXException if source document is not valid XML.
   */
  Document loadXML(URL url) throws IOException, SAXException;

  /**
   * Load XML document with name spaces support from source URL. Returned XML document uses UTF-8 encoding.
   * 
   * @param url source document URL.
   * @return newly created XML document.
   * @throws IOException if source document reading fails.
   * @throws SAXException if source document is not valid XML.
   */
  Document loadXMLNS(URL url) throws IOException, SAXException;

  // ----------------------------------------------------
  // create empty HTML document

  /**
   * Create empty HTML document.
   * 
   * @return new created HTML document.
   */
  Document createHTML();

  // ----------------------------------------------------
  // load HTML document from string source

  /**
   * Parse HTML document from source string. Returned document is not namespace aware.
   * 
   * @param string source string, null or empty not accepted.
   * @return newly created HTML document.
   * @throws SAXException if source string is not valid HTML document.
   */
  Document parseHTML(String string) throws SAXException;

  /**
   * Parse HTML document with namespace support from source string.
   * 
   * @param string source string, null or empty not accepted.
   * @return newly create HTML document.
   * @throws SAXException if source string is not valid HTML document.
   */
  Document parseHTMLNS(String string) throws SAXException;

  // ----------------------------------------------------
  // load HTML document from file

  /**
   * Load HTML document from source file using UTF-8 encoding. Returned document has not support for name space.
   * 
   * @param file not null, ordinary, source file.
   * @return newly created HTML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid HTML document.
   */
  Document loadHTML(File file) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from source file, using UTF-8 encoding.
   * 
   * @param file not null, ordinary, source file.
   * @return newly create HTML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid HTML document.
   */
  Document loadHTMLNS(File file) throws IOException, SAXException;

  /**
   * Load HTML document from file using specified character set. Returned document is not name space aware.
   * 
   * @param file not null, ordinary, source file.
   * @param encoding character set user to parse file content, null or empty not accepted.
   * @return newly created HTML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid HTML document.
   */
  Document loadHTML(File file, String encoding) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from source file, using specified character set.
   * 
   * @param file not null, ordinary, source file.
   * @param encoding character set user to parse file content, null or empty not accepted.
   * @return newly created HTML document.
   * @throws IOException if source file not found or reading fails.
   * @throws SAXException if source file is not a valid HTML document.
   */
  Document loadHTMLNS(File file, String encoding) throws IOException, SAXException;

  // ----------------------------------------------------
  // load HTML document from input stream

  /**
   * Load HTML document from bytes stream using UTF-8 character set. Returned document is not name space aware. Input
   * stream is closed after document load.
   * 
   * @param stream input bytes stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>stream</code> is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(InputStream stream) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from bytes stream, using UTF-8 character set. Input stream is closed
   * after document load.
   * 
   * @param stream input bytes stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>stream</code> is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(InputStream stream) throws IOException, SAXException;

  /**
   * Load HTML document from bytes stream using specified character set. Input stream is closed after document load.
   * 
   * @param stream input bytes stream,
   * @param encoding character set used to parse input stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>stream</code> is null.
   * @throws IllegalArgumentException if <code>encoding</code> is null or empty.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(InputStream stream, String encoding) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from bytes stream, using specified character set. Input stream is closed
   * after document load.
   * 
   * @param stream input bytes stream,
   * @param encoding character set used to parse input stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>stream</code> is null.
   * @throws IllegalArgumentException if <code>encoding</code> is null or empty.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(InputStream stream, String encoding) throws IOException, SAXException;

  // ----------------------------------------------------
  // load HTML document from reader

  /**
   * Load HTML document from characters stream using UTF-8 character set. Returned document is not name space aware.
   * Source reader is closed after document load.
   * 
   * @param reader input characters stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>reader</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(Reader reader) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from characters stream, using UTF-8 character set. Source reader is
   * closed after document load.
   * 
   * @param reader input characters stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>reader</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(Reader reader) throws IOException, SAXException;

  /**
   * Load HTML document from characters stream using specified character set. Returned document is not name space aware.
   * Source reader is closed after document load.
   * 
   * @param reader input characters stream,
   * @param encoding character set used to parse input stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>reader</code> argument is null.
   * @throws IllegalArgumentException if <code>encoding</code> argument is null or empty.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(Reader reader, String encoding) throws IOException, SAXException;

  /**
   * Load HTML document with name space support from characters stream, using specified character set. Source reader is
   * closed after document load.
   * 
   * @param reader input characters stream,
   * @param encoding character set used to parse input stream.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>reader</code> argument is null.
   * @throws IllegalArgumentException if <code>encoding</code> argument is null or empty.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(Reader reader, String encoding) throws IOException, SAXException;

  // ----------------------------------------------------
  // load HTML document from URL

  /**
   * Load HTML document from source URL using UTF-8 encoding.
   * 
   * @param url source URL.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>url</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(URL url) throws IOException, SAXException;

  /**
   * Load HTML document from source URL using specified encoding.
   * 
   * @param url source URL,
   * @param encoding character set used to parse source URL.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>url</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTML(URL url, String encoding) throws IOException, SAXException;

  /**
   * Load HTML document with support for name spaces, from source URL using UTF-8 encoding.
   * 
   * @param url source URL.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>url</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(URL url) throws IOException, SAXException;

  /**
   * Load HTML document with support for name spaces, from source URL using specified encoding.
   * 
   * @param url source URL,
   * @param encoding character set used to parse source URL.
   * @return newly created HTML document.
   * @throws IllegalArgumentException if <code>url</code> argument is null.
   * @throws SAXException 
   * @throws IOException 
   */
  Document loadHTMLNS(URL url, String encoding) throws IOException, SAXException;
}
