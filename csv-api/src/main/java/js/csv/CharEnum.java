package js.csv;

/**
 * Interface implemented by all character constants enumerations. All implementations, less {@link CsvQuote} have a
 * single character value and index argument from {@link #value(int...)} is not used. CSV quote enumeration has two
 * characters, opening and closing quote characters, that are accessed with index 0, respective 1.
 * <p>
 * While seems counter intuitive this interface rationales is to unify all character enumerations.
 * 
 * @author Iulian Rotaru
 */
public interface CharEnum
{
  /**
   * Get character value or, if index argument is provided, the value from specified index.
   * 
   * @param index optional character index.
   * @return character value.
   */
  char value(int... index);
}
