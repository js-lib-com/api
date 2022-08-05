package com.jslib.api.csv;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Reads objects of specified type from CSV stream via iterator or iterable interface.
 * <p>
 * In order to create reader instance one need a descriptor with columns mapping to Java class fields. So first thing is
 * to create CSV descriptor and define columns mapping. Then can create reader instance for underlying CSV byte or
 * character streams. Note that {@link CsvFormat#charset()} is used only if underlying stream is a byte stream.
 * 
 * <pre>
 * class Person
 * {
 *   String name;
 *   int age;
 * }
 * 
 * CsvFactory factory = ServiceLoader.load(CsvFactory.class).iterator().next();
 * CsvDescriptor<Person> descriptor = factory.getDescriptor(Person.class);
 * descriptor.columns("name", "age");
 * 
 * CsvReader<Person> reader = factory.getReader(descriptor, new FileReader("persons.csv"));
 * for(Person person : reader) {
 *   // do something with person
 * }
 * reader.close();
 * </pre>
 * 
 * @author Iulian Rotaru
 * @param <T> CSV objects type.
 */
public interface CsvReader<T> extends Iterator<T>, Iterable<T>, Closeable
{
}
