package com.jslib.api.csv;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Typed factory for CSV descriptors, readers and writers. This factory is the starting point for a CSV transaction.
 * Implementation is encouraged to distribute this factory as a standard Java service.
 * <p>
 * CSV readers and writers are used for actual CSV stream handling. In order to create them one need a descriptor with
 * at least columns mapping to Java class fields. So first thing is to create CSV descriptor and define columns mapping.
 * Then can create readers and writers for underlying CSV byte or character streams.
 * 
 * <pre>
 * CsvFactory factory = ServiceLoader.load(CsvFactory.class).iterator().next();
 * List<Person> persons = new ArrayList<>();
 * 
 * CsvDescriptor<Person> descriptor = factory.getDescriptor(Person.class);
 * descriptor.format().delimiter(CsvDelimiter.TAB).quote(CsvQuote.SQUARE_BRACKETS);
 * descriptor.columns("name", "age");
 * 
 * CsvReader<Person> reader = factory.getReader(descriptor, new FileReader("file.csv"));
 * for(Person person : reader) {
 *   persons.add(person);
 * }
 * reader.close();
 * 
 * CsvWriter<Person> writer = factory.getWriter(descriptor, new FileWriter("file.csv"));
 * for(Person person : persons) {
 *   writer.write(person);
 * }
 * writer.close();
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public interface CsvFactory
{
  /**
   * Create default descriptor for requested type. Created descriptor has default CSV format and no columns defined.
   * 
   * @param type Java class to map columns to.
   * @return created CSV descriptor.
   */
  <T> CsvDescriptor<T> getDescriptor(Class<T> type);

  /**
   * Create typed CSV reader and configure it from given descriptor. It uses external CSV source stream. This factory
   * method assume platform default character encoding and ignore charset from descriptor. If need to control character
   * encoding please use {@link #getReader(CsvDescriptor, InputStream)}.
   * 
   * @param descriptor typed CSV descriptor,
   * @param reader source CSV stream.
   * @param <T> bound Java class.
   * @return created CSV reader.
   */
  <T> CsvReader<T> getReader(CsvDescriptor<T> descriptor, Reader reader);

  /**
   * Alternative for {@link #getReader(CsvDescriptor, Reader)} when need to control character encoding. This variant
   * used charset declared by descriptor to parse given input stream.
   * 
   * @param descriptor typed CSV descriptor,
   * @param reader source CSV stream.
   * @param <T> bound Java class.
   * @return created CSV reader.
   */
  <T> CsvReader<T> getReader(CsvDescriptor<T> descriptor, InputStream stream);

  /**
   * Create typed CSV writer and configure it from given descriptor. It uses external CSV target stream. This factory
   * method assume platform default character encoding and ignore charset from descriptor. If need to control character
   * encoding please use {@link #getWriter(CsvDescriptor, OutputStream)}.
   * 
   * @param descriptor typed CSV descriptor,
   * @param writer target CSV stream.
   * @param <T> bound Java class.
   * @return created CSV writer.
   */
  <T> CsvWriter<T> getWriter(CsvDescriptor<T> descriptor, Writer writer);

  /**
   * Alternative for {@link #getWriter(CsvDescriptor, Writer)} when need to control character encoding. This variant
   * used charset declared by descriptor to serialize to given output stream.
   * 
   * @param descriptor typed CSV descriptor,
   * @param writer target CSV stream.
   * @param <T> bound Java class.
   * @return created CSV writer.
   */
  <T> CsvWriter<T> getWriter(CsvDescriptor<T> descriptor, OutputStream stream);
}
