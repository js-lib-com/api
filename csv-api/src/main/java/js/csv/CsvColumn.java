package js.csv;

import js.format.Format;

/**
 * A CSV column is mapped to a certain Java class field, identified by it name, and may have a value formatter. Value
 * formatter {@link Format#format(Object)} is used to convert field value to string when write to CSV stream whereas
 * {@link Format#parse(String)} is used to convert CSV string value to an object compatible with field type.
 * 
 * @author Iulian Rotaru
 */
public interface CsvColumn
{
  /**
   * The name of the Java class field related to this CSV column.
   * 
   * @return related field name.
   */
  String fieldName();

  /**
   * Optional value formatter instance used to pre- / post- process this CSV column value before writing, respective
   * after reading to / from CSV stream.
   * 
   * @return formatter instance or null.
   */
  Format formatter();
}
