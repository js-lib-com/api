package com.jslib.api.log;

/**
 * Logger context stores named data to current thread and all child threads. Thread named data can be injected into
 * every log message from thread till {@link #clear()} is called. These class is specifically designed for multiple
 * threads environments and allow to separate otherwise intermixed log messages. Implementation is expected to propagate
 * this log context on child threads, most probable using {@link InheritableThreadLocal}.
 * 
 * Here is an example of a hypothetical servlet. On every request, service method stores context name and remote address
 * with <code>app</code>, respective <code>ip</code> names, on log context; every log message will include context name
 * and remote address no matter if called directly from servlet service or from a nested method.
 * 
 * <pre>
 * protected void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
 * 	// store request remote address to current thread
 *  LogFactory.getLogContext().put("app", httpRequest.getContextPath());
 *  LogFactory.getLogContext().put("ip", httpRequest.getRemoteHost());
 * 	try {
 * 		// log message can be configured to include request remote address
 * 		log.debug(...);
 * 	}
 * 	finally {
 * 		// takes care to cleanup request remote address from current thread since thread can be reused
 * 		LogFactory.getLogContext().clear();
 * 	}
 * }
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public interface LogContext
{
  /**
   * Put named data on logger context, on current thread locale. Context data will reside on current thread till thread
   * terminates or {@link #clear()} is called on current thread. Overrides value if context diagnostic data already
   * exists.
   * 
   * If given <code>name</code> is null or empty this method silently does nothing and if <code>value</code> is null
   * remove named context data.
   * 
   * @param name name for context data, not tolerated,
   * @param value diagnostic context value, possible null.
   */
  void put(String name, String value);

  /**
   * Return logger context data by name, from current thread, or null if named data is missing.
   * 
   * @param name name for context data, not null.
   * @return logger context data, possible null.
   */
  String get(String name);

  /** Cleanup all context data related to current thread. */
  void clear();

}
