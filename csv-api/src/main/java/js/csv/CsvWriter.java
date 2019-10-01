package js.csv;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Write objects to CSV stream.
 * <p>
 * Sample code.
 * 
 * <pre>
 * CsvFactory factory = ServiceLoader.load(CsvFactory.class).iterator().next();
 * List<Person> persons = new ArrayList<>();
 * 
 * CsvWriter<Person> writer = factory.getWriter(Person.class);
 * for(Person person : persons) {
 *   writer.write(person);
 * }
 * writer.close();
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public interface CsvWriter<T> extends Flushable, Closeable
{
  void write(T object) throws IOException;
}
