# Typed CSV Interface. 

Simplified CSV API for mapping CSV records to typed list of objects.

```
CsvFactory factory = ServiceLoader.load(CsvFactory.class).iterator().next();

CsvDescriptor<Person> descriptor = factory.getDescriptor(Person.class);
descriptor.columns("name", "address");
```

```
CsvReader<Person> reader = factory.getReader(descriptor, csvStream);
for(Person person : reader) {
	// person instance initialized from CSV record
}
reader.close();
```

```
CsvWriter<Person> writer = factory.getWriter(descriptor, csvStream);
for(Person person : persons) {
	writer.write(person);
}
writer.close();
```
 
This API contains SPI details but is too simple to break it in two separated packages.
