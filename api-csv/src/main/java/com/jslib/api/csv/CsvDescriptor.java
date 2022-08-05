package com.jslib.api.csv;

import java.util.List;

import com.jslib.format.Format;

/**
 * Descriptor contains a reference to CSV format and methods to manage CSV columns mapping to class fields. CSV format
 * contains properties for related CSV stream and columns mapping is bound to a specific Java class. CSV columns order
 * should be the order from CSV stream. In the absence of header - which is optional, there is no way to identify a
 * column beside its position; so keep in mind that columns position matters.
 * <p>
 * Columns can be loaded from CSV stream header, see {@link #load(List)} or can be added in group or one by one.
 * 
 * @author Iulian Rotaru
 * @param <T> bound Java class.
 */
public interface CsvDescriptor<T>
{
  /**
   * Reference to CSV format containing properties of related CSV stream.
   * 
   * @return related CSV format.
   */
  CsvFormat format();

  /**
   * Java class bound to this CSV descriptor.
   * 
   * @return bound Java class.
   */
  Class<T> type();

  /**
   * Load columns definition from CSV header. Remember that CSV header is optional and, if present, it is the first
   * record from CSV stream. CSV header should contain column names compatible with bound class fields.
   * <p>
   * There are two naming convention: Java and non Java names. Java names are standard field names. Non Java names are
   * words separated by underscore ('_'), dash ('-') or space (' '), e.g. "NAME", "POSTAL_ADDRESS", "phone-number",
   * "parking code";
   * <p>
   * Implementation should detect used naming convention and convert, if necessary, to Java field name. Above name
   * examples should be converted to "name", "postalAddress", "phoneNumber", "parkingCode".
   * 
   * @param header CSV header.
   */
  void load(List<String> header);

  /**
   * Initialize column definitions from given array of field names. Field names should be listed in the columns order,
   * as present on CSV stream. No column has value formatter.
   * <p>
   * This method should be invoked on an empty descriptor. Already defined columns, if any, are overwritten. If format
   * is strict, see {@link CsvFormat#strict()}, implementation should check every field name for actually exiting in
   * bound class and throw exception if missing.
   * 
   * @param fieldNames standard field names in proper order.
   * @return this pointer.
   * @throws CsvException if CSV format is strict and a named field does not exist in descriptor bound class.
   */
  CsvDescriptor<T> columns(String... fieldNames) throws CsvException;

  /**
   * Initialize column definitions from given enumeration constants. Similar to {@link #columns(String...)} but convert
   * enumeration names to standard Java field name.
   * 
   * @param columnNames enumeration constants.
   * @return this pointer.
   * @throws IllegalArgumentException if column names argument is null.
   * @throws CsvException if CSV format is strict and a named field does not exist in descriptor bound class.
   */
  CsvDescriptor<T> columns(Class<? extends Enum<?>> columnNames) throws IllegalArgumentException, CsvException;

  /**
   * Add column definitions for given field name. Resulting columns order should be consistent with CSV columns from CSV
   * stream. Argument should be a valid Java field name. If CSV format is strict implementation should check if field
   * does actually exist and throw exception if missing.
   * <p>
   * Added column mapping has no value formatter.
   * 
   * @param fieldName field name to add column mapping for.
   * @return this pointer.
   * @throws IllegalArgumentException if field name argument is null or empty.
   * @throws CsvException if CSV format is strict and a named field does not exist in descriptor bound class.
   */
  CsvDescriptor<T> column(String fieldName) throws IllegalArgumentException, CsvException;

  /**
   * Add column definitions for given field name and value formatter. Resulting columns order should be consistent with
   * CSV columns from CSV stream. Field name argument should be a valid Java field name. If value formatter is null this
   * method behaves similar to {@link #column(String)}.
   * <p>
   * If CSV format is strict implementation should check if field does actually exist and throw exception if missing.
   * 
   * @param fieldName field name to add column mapping for,
   * @param formatter value formatter, null accepted.
   * @return this pointer.
   * @throws IllegalArgumentException if field name argument is null or empty.
   * @throws CsvException if CSV format is strict and a named field does not exist in descriptor bound class.
   */
  CsvDescriptor<T> column(String fieldName, Format formatter) throws IllegalArgumentException, CsvException;

  /**
   * Get defined columns. Return empty list if columns are not defined yet.
   * <p>
   * Returned columns order should be consistent with CSV columns from CSV stream.
   * 
   * @return defined columns, possible empty.
   */
  List<CsvColumn> columns();
}
