package com.jslib.api.log;

/**
 * Provider interface for asynchronous logging services. Provider is actually a factory in charge with logging objects
 * creation: {@link Log} and {@link LogContext}; it is delegated by logging system facade, {@link LogFactory}.
 * 
 * Service provider is encouraged to write logging event asynchronously. Provider configuration is implementation
 * detail.
 * 
 * Service provider should be implemented as Java service and should include META-INF/services descriptor.
 * 
 * @author Iulian Rotaru
 */
public interface LogProvider
{
  LogConfig getLogConfig();
  
  /**
   * Get the instance of logger context usable to store diagnostic context data on current thread.
   * 
   * @return logger context instance.
   */
  LogContext getLogContext();

  /**
   * Create named logger instance.
   * 
   * @param loggerName logger name.
   * @return logger instance.
   */
  Log getLogger(String loggerName);

  /**
   * Force immediate flush all log messages to target media and release resources, like threads for asynchronous
   * appenders. It is intended to be called on container / application destruction to ensure there are no messages loss.
   */
  void close();
}
