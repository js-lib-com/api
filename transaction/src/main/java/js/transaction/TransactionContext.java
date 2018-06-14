package js.transaction;

/**
 * Transaction context provides access to currently executing session on transactional resource. Transaction context is designed
 * to work only with transactional container and the only means to obtain it is via dependency injection.
 * <p>
 * A transactional resource offers two kinds of APIs: transaction API exposes by {@link Transaction} implementation and worker
 * API exposed by session instance, executed inside boundaries specified by transaction API. Worker API is used to actually
 * fulfill the working unit.
 * <p>
 * In sample code below, transaction context is injected into a DAO implementation and facilitate access to currently executing
 * session. Session on its turn is used to perform the actual transactional working unit.
 * 
 * <pre>
 * class DaoImpl implements Dao
 * {
 * 	{@literal @}DependencyInjection
 * 	private TransactionContext context;
 * 	...
 * 	User getUser(int userId) { 
 * 		Session session = context.getSession();
 * 		Query query = session.createQuery(...);
 * 		...
 * 	}
 * }
 * </pre>
 * <p>
 * In this context, the term <code>session</code> is used in a broader sense. There are implementations naming it indeed
 * <code>session</code> or, for example JDBC, uses <code>connection</code>.
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface TransactionContext {
	/**
	 * Get currently executing session on transactional resource. Session exposes transactional resource API used to fulfill
	 * transactional working unit.
	 * <p>
	 * Session type depends on implementation, e.g. on Hibernate is named indeed <code>session</code> while on JDBC is
	 * <code>connection</code>.
	 * 
	 * @return currently executing session.
	 * @param <T> auto-cast to session type.
	 */
	<T> T getSession();
}
