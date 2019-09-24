package js.transaction;

/**
 * A transaction supplies methods to mark transaction boundaries. Transactional working unit is executed between this
 * transaction instance creation and transaction commit. Transaction instance is obtained from transaction manager, see
 * {@link TransactionManager#createTransaction(String)}. If working unit or commit fails transaction is rolled back. After working
 * unit completes, successful or erroneous transaction instance should be closed.
 * <p>
 * Here is standard use case.
 * 
 * <pre>
 * Transaction t = manager.createTransaction();
 * try {
 * 	// execute transactional working unit
 * 	t.commit();
 * } catch (Exception e) {
 * 	t.rollback();
 * } finally {
 * 	t.close();
 * }
 * </pre>
 * 
 * <p>
 * Read only transaction does not explicit use commit or rollback. Just create transaction and close after finishing working
 * unit.
 * <p>
 * This interface is intended to be used by transactional containers and is part of service provider interface. Implementation
 * is required to support nested transactions so that a transactional method can be invoked from another transactional method.
 * Both {@link #commit()} and {@link #rollback()} should be actually executed on transactional resource only when outermost
 * transactional method completes. Also {@link #close()} should release resources also only for outermost transaction.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface Transaction {
	/**
	 * Commit current transactional working unit. Changes performed by working unit on the transactional resource do actually
	 * take effect.
	 * 
	 * @throws IllegalStateException if attempt to commit a read-only transaction.
	 * @throws TransactionException if commit fails.
	 */
	void commit();

	/**
	 * Rollback current transaction if working unit fails.
	 * 
	 * @throws IllegalStateException if attempt to rollback a read-only transaction.
	 * @throws TransactionException if rollback fails.
	 */
	void rollback();

	/**
	 * Close transaction after commit or even roolback to ensure resources used by transaction are released. Implementation
	 * should track nested transactions and actually release resources only for the outermost transaction.
	 * 
	 * @return true if transaction was closed and resources released; return false if this transaction is a nested one.
	 * @throws TransactionException if close fails.
	 */
	boolean close();

	/**
	 * Test if a method was declared superfluously as transactional. A transaction is considered used if {@link #getSession()}
	 * was invoked from user code, of course before transaction concluding with commit or rollback. This value has meaning for a
	 * short time between transaction conclude and close.
	 * <p>
	 * This predicate is for debugging purposes only.
	 * 
	 * @return if transaction was not used.
	 */
	boolean unused();

	/**
	 * Get underlying session used by this transaction instance to actually operates on the transactional resource. In this
	 * context, <code>session</code> term is used in a broad sense; it denotes meanings that facilitates interaction with
	 * transactional resource and allocated per transaction period.
	 * <p>
	 * Session type depends on implementation, e.g. on Hibernate is named indeed <code>session</code> while on JDBC is
	 * <code>connection</code>.
	 * 
	 * @return this transaction underlying session.
	 * @param <T> auto-cast to underlying session type.
	 */
	<T> T getSession();
}
