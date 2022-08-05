package com.jslib.api.csv;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Write objects to CSV stream.
 * <p>
 * In order to create writer instance one need a descriptor with columns mapping to Java class fields. So first thing is
 * to create CSV descriptor and define columns mapping. Then can create writer instance for underlying CSV byte or
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
 * CsvWriter<Person> writer = factory.getWriter(descriptor, new FileWriter("persons.csv"));
 * List<Person> persons = ... load persons from some data source
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
