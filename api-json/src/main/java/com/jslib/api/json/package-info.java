/**
 * JSON Parser and Serializer. JSON package uses {@link com.jslib.api.json.Json} facade to supplies (de)serialization services.
 * As a consequence using JSON package is straightforward: invoke facade supplied methods, see samples.
 * <pre>
 * Json json = ServiceLoader.load(Json.class).iterator().next();
 * ...
 * 
 * // parse Page instance from JSON object stream
 * Page page = json.parse(reader, Page.class);
 *	
 * // parse not homogeneous array from JSON array stream
 * Object[] parameters = json.parse(reader, new Type[] { double.class, Page.class, String.class, boolean.class });
 *  
 * // parse list of objects from JSON formatted string  
 * List&lt;Page&gt; pages = json.parse(jsonList, new GType(List.class, Page.class));
 *
 * // serialize Page instance on JSON stream  
 * json.stringify(writer, page);
 *
 * // serialize Page instance to JSON formatted string
 * String jsonObject = json.stringify(page);
 * </pre>
 * Method names used on JSON facade should be familiar to ECMA Script programmers.
 * 
 * <h3>Parameterized Types</h3>
 * Parameterized types are supported and processed reflectively. This is based on Java API stating in couple 
 * classes reference that parameterized types are present into bytecode. For example see this excerpt from 
 * Method.getGenericParameterTypes API: <em>If a formal parameter type is a parameterized type, the Type object 
 * returned for it must accurately reflect the actual type parameters used in the source code.</em>
 * <p>
 * Anyway, do not confuse with type variables, a.k.a. type parameters. Those are not present into bytecode and are
 * not considered by JSON package logic.
 * 
 * <h3>Best Effort</h3>
 * JSON parser from this API is relaxed and follows a best effort approach. If a named property from JSON stream 
 * does not have the same name field into target object, parser just warn on log and ignore. Also, fields from target 
 * object with no values into JSON stream are set to null. On serialization all fields are processed less static and transient.
 * 
 * <h3>Inband Type Information</h3>
 * JSON parser and serializer support an extension to JSON protocol that allows for inband type information processing.
 * It is named inband because type information is serialized together with value object, in the same stream. A JSON stream 
 * with inbad type information may look like below. Please note <code>class</code> property from JSON start.
 * <pre>
 *  {"class":"js.net.Event","id":1234 ... }
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
package com.jslib.api.json;

