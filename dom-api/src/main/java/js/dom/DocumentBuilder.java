package js.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import org.xml.sax.InputSource;

/**
 * Document object builder. Supply factory methods for documents creation, parsing from string and loading from various sources:
 * file, input stream, input source and URL. There are different factory methods for XML and HTML documents and all are in two
 * flavors: with or without name space support. For name space support this class follows W3C DOM notation convention and uses
 * <code>NS</code> suffix.
 * <p>
 * All loaders use XML declaration or HTML meta Content-Type to choose characters encoding; anyway, loader variant using input
 * source can force a particular encoding.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface DocumentBuilder {
	/**
	 * Create empty XML document with given root element. Created document is not name space aware and uses UTF-8 for character
	 * encoding.
	 * 
	 * @param root name of the root element.
	 * @return newly created XML document.
	 */
	Document createXML(String root);

	/**
	 * Create a new XML document with support for name spaces. Created document uses UTF-8 for character encoding.
	 * 
	 * @param root root element tag,
	 * @return newly created document.
	 */
	Document createXMLNS(String root);

	// ----------------------------------------------------
	// parse XML document from string source

	/**
	 * Parse XML document form source string. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param string source string.
	 * @return newly created document.
	 */
	Document parseXML(String string);

	/**
	 * Parse XML document from source string with support for name spaces. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param string source string, UTF-8.
	 * @return newly created document instance.
	 */
	Document parseXMLNS(String string);

	// ----------------------------------------------------
	// load XML document from file

	/**
	 * Load XML document from file. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param file source file.
	 * @return newly created XML document.
	 * @throws FileNotFoundException if source file is missing.
	 */
	Document loadXML(File file) throws FileNotFoundException;

	/**
	 * Load XML document with name spaces support from file. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param file source file,
	 * @return newly created XML document.
	 * @throws FileNotFoundException if source file does not exist.
	 */
	Document loadXMLNS(File file) throws FileNotFoundException;

	// ----------------------------------------------------
	// load XML document from input stream

	/**
	 * Load XML document from input stream. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param stream input stream.
	 * @return newly created XML document.
	 */
	Document loadXML(InputStream stream);

	/**
	 * Load XML document with name spaces support from input stream. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param stream input stream,
	 * @return newly created XML document.
	 */
	Document loadXMLNS(InputStream stream);

	// ----------------------------------------------------
	// load XML document from reader

	/**
	 * Load XML document from reader. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param reader source reader.
	 * @return newly created XML document.
	 */
	Document loadXML(Reader reader);

	/**
	 * Load XML document with name spaces support from reader. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param reader source reader,
	 * @return newly created XML document.
	 */
	Document loadXMLNS(Reader reader);

	// ----------------------------------------------------
	// load XML document from input source

	/**
	 * Load XML document from input source. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param source input source.
	 * @return newly created XML document.
	 */
	Document loadXML(InputSource source);

	/**
	 * Load XML document with name spaces support from input source. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param source input source.
	 * @return newly created XML document.
	 */
	Document loadXMLNS(InputSource source);

	// ----------------------------------------------------
	// load XML document from URL

	/**
	 * Load XML document from source URL. Returned document is not name space aware and uses UTF-8 encoding.
	 * 
	 * @param url source URL.
	 * @return newly created XML document.
	 */
	Document loadXML(URL url);

	/**
	 * Load XML document with name spaces support from source URL. Returned XML document uses UTF-8 encoding.
	 * 
	 * @param url source URL.
	 * @return newly created XML document.
	 */
	Document loadXMLNS(URL url);

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
	 * Parse HTML document from source string. Returned document is not name space aware.
	 * 
	 * @param string source string.
	 * @return newly created HTML document.
	 */
	Document parseHTML(String string);

	/**
	 * Parse HTML document with name space support from source string.
	 * 
	 * @param string source string.
	 * @return newly create HTML document.
	 */
	Document parseHTMLNS(String string);

	// ----------------------------------------------------
	// load HTML document from file

	/**
	 * Load HTML document from source file using UTF-8 encoding. Returned document has not support for name space.
	 * 
	 * @param file source file.
	 * @return newly created HTML document.
	 * @throws FileNotFoundException if source file does not exist.
	 */
	Document loadHTML(File file) throws FileNotFoundException;

	/**
	 * Load HTML document with name space support from source file, using UTF-8 encoding.
	 * 
	 * @param file source file.
	 * @return newly create HTML document.
	 * @throws FileNotFoundException if source file does not exist.
	 */
	Document loadHTMLNS(File file) throws FileNotFoundException;

	/**
	 * Load HTML document from file using specified character set. Returned document is not name space aware.
	 * 
	 * @param file source file,
	 * @param encoding character set user to parse file content.
	 * @return newly created HTML document.
	 * @throws FileNotFoundException if source file does not exist.
	 */
	Document loadHTML(File file, String encoding) throws FileNotFoundException;

	/**
	 * Load HTML document with name space support from source file, using specified character set.
	 * 
	 * @param file source file,
	 * @param encoding character set user to parse file content.
	 * @return newly created HTML document.
	 * @throws FileNotFoundException if source file does not exist.
	 */
	Document loadHTMLNS(File file, String encoding) throws FileNotFoundException;

	// ----------------------------------------------------
	// load HTML document from input stream

	/**
	 * Load HTML document from bytes stream using UTF-8 character set. Returned document is not name space aware.
	 * 
	 * @param stream input bytes stream.
	 * @return newly created HTML document.
	 */
	Document loadHTML(InputStream stream);

	/**
	 * Load HTML document with name space support from bytes stream, using UTF-8 character set.
	 * 
	 * @param stream input bytes stream.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(InputStream stream);

	/**
	 * Load HTML document from bytes stream using specified character set.
	 * 
	 * @param stream input bytes stream,
	 * @param encoding character set used to parse input stream.
	 * @return newly created HTML document.
	 */
	Document loadHTML(InputStream stream, String encoding);

	/**
	 * Load HTML document with name space support from bytes stream, using specified character set.
	 * 
	 * @param stream input bytes stream,
	 * @param encoding character set used to parse input stream.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(InputStream stream, String encoding);

	// ----------------------------------------------------
	// load HTML document from reader

	/**
	 * Load HTML document from characters stream using UTF-8 character set. Returned document is not name space aware.
	 * 
	 * @param reader input characters stream.
	 * @return newly created HTML document.
	 */
	Document loadHTML(Reader reader);

	/**
	 * Load HTML document with name space support from characters stream, using UTF-8 character set.
	 * 
	 * @param reader input characters stream.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(Reader reader);

	/**
	 * Load HTML document from characters stream using specified character set. Returned document is not name space aware.
	 * 
	 * @param reader input characters stream,
	 * @param encoding character set used to parse input stream.
	 * @return newly created HTML document.
	 */
	Document loadHTML(Reader reader, String encoding);

	/**
	 * Load HTML document with name space support from characters stream, using specified character set.
	 * 
	 * @param reader input characters stream,
	 * @param encoding character set used to parse input stream.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(Reader reader, String encoding);

	// ----------------------------------------------------
	// load HTML document from input source

	/**
	 * Load HTML document from input source using UTF-8 character set. Returned document is not name space aware.
	 * 
	 * @param source input source.
	 * @return newly created HTML document.
	 */
	Document loadHTML(InputSource source);

	/**
	 * Load HTML document with name space support from input source, using UTF-8 character set.
	 * 
	 * @param source input source.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(InputSource source);

	/**
	 * Load HTML document from input source using specified character set. Returned document is not name space aware.
	 * 
	 * @param source input source,
	 * @param encoding character set used to parse input source.
	 * @return newly created HTML document.
	 */
	Document loadHTML(InputSource source, String encoding);

	/**
	 * Load HTML document with name space support from input source, using specified character set.
	 * 
	 * @param source input source,
	 * @param encoding character set used to parse input source.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(InputSource source, String encoding);

	// ----------------------------------------------------
	// load HTML document from URL

	/**
	 * Load HTML document from source URL. For parsing uses character set specified by <code>Content-Type</code> header from
	 * HTTP response.
	 * 
	 * @param url source URL.
	 * @return newly created HTML document.
	 */
	Document loadHTML(URL url);

	/**
	 * Load HTML document with support for name spaces, from source URL. For parsing uses character set specified by
	 * <code>Content-Type</code> header from HTTP response.
	 * 
	 * @param url source URL.
	 * @return newly created HTML document.
	 */
	Document loadHTMLNS(URL url);
}
