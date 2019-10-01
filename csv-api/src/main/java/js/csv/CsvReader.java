package js.csv;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Reads objects of specified type from CSV stream via iterator or iterable interface.
 * <p>
 * Sample code.
 * 
 * <pre>
 * class Person
 * {
 *   String name;
 *   int age;
 * }
 * 
 * List<Person> persons = new ArrayList<>();
 * 
 * CsvReader<Person> reader = factory.getReader(new FileReader("file.csv"), Person.class);
 * for(Person person : reader) {
 *   persons.add(person);
 * }
 * reader.close();
 * </pre>
 * 
 * @author Iulian Rotaru
 * @param <T> CSV objects type.
 * @version draft
 */
public interface CsvReader<T> extends Iterator<T>, Iterable<T>, Closeable
{
}
