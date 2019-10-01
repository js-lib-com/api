package js.csv;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import js.lang.Config;
import js.lang.ConfigException;

/**
 * Typed factory for CSV readers and writers.
 * <p>
 * Sample code.
 * 
 * <pre>
 * CsvFactory factory = ServiceLoader.load(CsvFactory.class).iterator().next();
 * List<Person> persons = new ArrayList<>();
 * 
 * CsvReader<Person> reader = factory.getReader(new FileReader("file.csv"), Person.class);
 * for(Person person : reader) {
 *   persons.add(person);
 * }
 * reader.close();
 * 
 * CsvWriter<Person> writer = factory.getWriter(new FileWriter("file.csv"), Person.class, config);
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
  <T> CsvDescriptor<T> getDescriptor(Class<T> type);

  <T> CsvDescriptor<T> getDescriptor(Class<T> type, Config config) throws ConfigException;

  <T> CsvDescriptor<T> getDescriptor(Config config) throws ConfigException;

  <T> CsvReader<T> getReader(CsvDescriptor<T> descriptor, Reader reader);

  <T> CsvReader<T> getReader(CsvDescriptor<T> descriptor, InputStream stream);

  /**
   * <pre>
   * <?xml version="1.0" encoding="UTF-8"?>
   * <csv class="com.domain.Person" separator="comma" null-value="null" debug="true">
   *   <value name="Name" property="name" />
   *   <value name="Age" property="age" />
   * </csv>
   * </pre>
   * 
   * Implementation should match declared class from configuration object against type argument and throw configuration
   * exception.
   * 
   * @param writer
   * @param type
   * @param config
   * @return
   * @throws ConfigException
   */
  <T> CsvWriter<T> getWriter(CsvDescriptor<T> descriptor, Writer writer);

  <T> CsvWriter<T> getWriter(CsvDescriptor<T> descriptor, OutputStream stream);
}
