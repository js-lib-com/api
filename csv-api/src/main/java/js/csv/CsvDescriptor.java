package js.csv;

import java.util.List;

import js.format.Format;
import js.util.Strings;

/**
 * CVS columns mappings to bound class fields.
 * 
 * @author Iulian Rotaru
 */
public interface CsvDescriptor<T>
{
  CsvFormat format();

  Class<T> type();

  /**
   * Load columns definition from CSV header. Remember that CSV header is optional and, if present, it is the first
   * record from CSV stream. CSV header should contain column names compatible with bound class fields. Implementation
   * should use {@link Strings#toMemberName(String)} to convert column name to Java field name.
   * <p>
   * TODO: add columns name description
   * 
   * @param header CSV header.
   */
  void load(List<String> header);

  CsvDescriptor<T> columns(String... fieldNames);

  CsvDescriptor<T> columns(Class<? extends Enum<?>> columnNames);

  CsvDescriptor<T> column(String fieldName);

  CsvDescriptor<T> column(String fieldName, Format formatter);

  /**
   * Get defined columns. Return empty list if columns are not defined yet.
   * <p>
   * Returned columns order should be consistent with CSV columns from CSV stream. In the absence of header - which is
   * optional, there is no way to identify a column beside its position.
   * 
   * @return defined columns possible empty.
   */
  List<CsvColumn> columns();
}
