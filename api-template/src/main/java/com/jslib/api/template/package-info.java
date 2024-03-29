/**
 * Declarative Templates Engine. This API describe a simple template engine interface. Being so simple this API is
 * generic enough to deal with any kind of templates. Anyway, author highly recommend declarative implementation; this
 * means it should have descriptive operators and it should not embed a programming language. Declarative nature is
 * mandatory mainly when use templates engine for server pages generation. The goal is a clear separation of
 * presentation and business logic; it is not acceptable to mix-in business rules into presentation layer.
 * 
 * <p>
 * Recommend way to deploy templates engine API is Java service. It is also recommended to cache and reuse template
 * engine but is legal to have multiple instances with different properties. For this reason template engine
 * implementation should be reusable and thread safe.
 * 
 * <p>
 * Template engine does not manage templates repository; it creates template instance based on given characters stream.
 * It is developer responsibility to provide the right document format and developer should know template implementation
 * syntax.
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
 * 
 * Using templates engine is straightforward: obtain engine instance and used it to create template instances. Then use
 * {@link com.jslib.api.template.Template#serialize(Object, java.io.Writer)} to inject domain model and write resulting document to
 * a standard writer. Both templates engine and template instance are reusable and thread safe.
 * 
 * <h3>Domain Model Injection</h3>
 * <p>
 * In order to not alter domain model with interfaces specialized only for presentation, author recommends using
 * reflection. If use object fields or getter methods is implementation detail. Anyway, using fields access comes with
 * two advantages:
 * <ol>
 * <li>domain model interface should satisfy only business logic needs; it is not advisable to add getters not strictly
 * requested by business,
 * <li>when using getters, developer may be tempted to manipulate model fields in order to fill presentation needs
 * resulting in impedance mismatch.
 * </ol>
 * 
 * <h3>Template Syntax</h3>
 * <p>
 * Basically a template is a text document with holes in which domain model should be injected. There is clearly the
 * need for a syntax to bind model fields to document structure. For this, implementation is recommended to use
 * declarative syntax for template operators.
 * <p>
 * To give an idea how declarative syntax looks, here are two real world examples: reference X(HT)ML implementation and
 * Velocity.
 * 
 * <h3>Reference X(HT)ML Implementation</h3>
 * 
 * <pre>
 * &lt;ul data-list="users"&gt;
 * 	&lt;li&gt;
 * 		&lt;img data-src="picture" /&gt;
 * 		&lt;h4 data-text="name"&gt;&lt;/h4&gt;
 * 	&lt;/li&gt;
 * &lt;/ul&gt;
 * </pre>
 * 
 * <h3>Velocity</h3>
 * 
 * <pre>
 * &lt;ul&gt;
 * 	#foreach($user in $users)
 * 	&lt;li&gt;
 * 		&lt;img src="$user.picture" /&gt;
 * 		&lt;h4&gt;$user.name&lt;/h4&gt;
 * 	&lt;/li&gt;
 * 	#end
 * &lt;/ul&gt;
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
package com.jslib.api.template;