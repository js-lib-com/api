package js.csv;

import js.format.Format;

public interface CsvColumn
{
  /**
   * The name of the Java class field related to this CSV column.
   * 
   * @return related field name.
   */
  String fieldName();

  /**
   * Optional formatter instance used to pre- / post- process this CSV column value before writing, respective after
   * reading to / from CSV stream.
   * 
   * @return formatter instance or null.
   */
  Format formatter();
}
