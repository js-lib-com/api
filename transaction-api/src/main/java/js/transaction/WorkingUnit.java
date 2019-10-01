package js.transaction;

/**
 * Transactional working unit, programmatic alternative to the declarative transaction. This interface is designed to
 * work with {@link TransactionManager#exec(WorkingUnit, Object...)} method.
 * <p>
 * In sample code below, working unit is executed inside transaction boundaries; user code should be aware of that and
 * do not execute excessive long processing.
 * 
 * <pre>
 * Address address = transactionManager.exec(new WorkingUnit&lt;Session, Address&gt;() {
 *   public Address exec(Session session, Object... args) throws Exception {
 *     Person person = (Person)args[0];
 *     SQLQuery sql = session.createSQLQuery("SELECT * FROM address ...");
 *     ...
 *     return (Address)sql.uniqueResult();
 *   }
 * }, person);
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 * @param <S> transactional session
 * @param <T> returned value type.
 */
public interface WorkingUnit<S, T>
{
  /**
   * Execute block of code inside transactional boundaries.
   * 
   * @param session transactional session,
   * @param args variable number of arguments
   * @return working unit result, possible null.
   * @throws Exception any execution exception is bubbled up to {@link TransactionManager#exec(WorkingUnit, Object...)}.
   */
  T exec(S session, Object... args) throws Exception;
}
