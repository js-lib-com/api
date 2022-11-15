package com.jslib.api.log;

import java.net.URI;
import java.util.ServiceLoader;

/**
 * Logging system facade. To simplify integration log factory is a utility class; it provides static methods and is not
 * instantiable. Log factory maintains a static reference to logger provider that is initialized at this class loading.
 * If no implementation found on run-time, provider is initialized with no operation default provider, see
 * {@link DefaultLogProvider}.
 * 
 * Log factory main job is to create named logger instances and for that delegates
 * {@link LogProvider#getLogger(String)}. There is also a convenient factory method to create logger per class,
 * {@link #getLog(Class)}.
 * 
 * <pre>
 * public class Sample {
 * 	private static final Log log = LogFactory.getLog(Sample.class);
 * 	...
 * }
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public final class LogFactory
{
  /** Logger implementation provider. */
  private static LogProvider provider = provider();

  /**
   * Load logging service provider from Java services and return the first instance found. It is expected to have only
   * one logging service provider deployed on runtime; it more found just blindly select the first found.
   * 
   * Returns no operation provider, see {@link DefaultLogProvider}, if no logging service provider found.
   * 
   * @return log provider instance.
   */
  private static LogProvider provider()
  {
    Iterable<LogProvider> providers = ServiceLoader.load(LogProvider.class);
    for(LogProvider provider : providers) {
      // for now ignore multiple implementations and choose blindly the first one
      return provider;
    }
    return new DefaultLogProvider();
  }

  /** Prevent default constructor synthesis. */
  private LogFactory()
  {
  }

  /**
   * Set logger provider. Usually logger provider is deployed as Java service and is initialized at this class loading
   * and initialization by {@link #provider()} static method. This setter is an alternative for logger providers not
   * deployed as Java services.
   * 
   * @param provider logger provider.
   */
  public static void setProvider(LogProvider provider)
  {
    LogFactory.provider = provider;
  }

  public static LogConfig getLogConfig()
  {
    return provider.getLogConfig();
  }

  /**
   * Get logger context usable to bind diagnostic data to current thread.
   * 
   * @return logger diagnostic context.
   */
  public static LogContext getLogContext()
  {
    return provider.getLogContext();
  }

  /**
   * Create a named logger. By convention logger name is the name of the class for which it is created. This method is
   * here for completeness and expected to be used in special cases. This method delegates
   * {@link LogProvider#getLogger(String)}.
   * 
   * @param loggerName logger name.
   * @return newly created logger.
   */
  public static Log getLog(String loggerName)
  {
    return provider.getLogger(loggerName);
  }

  /**
   * Create logger for given class. This method just delegates {@link LogProvider#getLogger(String)} with class name as
   * argument.
   * 
   * @param targetClass class to create logger for.
   * @return newly created class logger.
   */
  public static Log getLog(Class<?> targetClass)
  {
    return provider.getLogger(targetClass.getName());
  }

  /**
   * Close provider. See {@link LogProvider#flush()}.
   */
  public static void close()
  {
    provider.close();
  }

  // ----------------------------------------------------------------------------------------------

  /**
   * Default, no operation logging service provider.
   * 
   * @author Iulian Rotaru
   */
  private static final class DefaultLogProvider implements LogProvider
  {
    private final LogConfig config = new DefaultLogConfig();
    private final LogContext context = new DefaultLogContext();
    private final Log log = new DefaultLog();

    @Override
    public LogConfig getLogConfig()
    {
      return config;
    }

    @Override
    public LogContext getLogContext()
    {
      return context;
    }

    @Override
    public Log getLogger(String loggerName)
    {
      return log;
    }

    @Override
    public void close()
    {
    }
  }

  private static final class DefaultLogConfig implements LogConfig
  {
    @Override
    public void setServerAddress(URI address)
    {
    }

    @Override
    public URI getServerAddress()
    {
      return null;
    }

    @Override
    public void setRootLevel(Level level)
    {
    }

    @Override
    public Level getRootLevel()
    {
      return null;
    }

    @Override
    public void setLoggerLevel(String loggerName, Level level)
    {
    }

    @Override
    public Level getLoggerLevel(String loggerName)
    {
      return null;
    }

    @Override
    public void clearLoggerLevel(String loggerName)
    {
    }

    @Override
    public void setFilter(String filter)
    {
    }

    @Override
    public String getFilter()
    {
      return null;
    }

    @Override
    public void clearFilter()
    {
    }

    @Override
    public void commit()
    {
    }
  }

  private static final class DefaultLogContext implements LogContext
  {
    @Override
    public void put(String name, String value)
    {
    }

    @Override
    public String get(String name)
    {
      return null;
    }

    @Override
    public void clear()
    {
    }
  }

  private static final class DefaultLog implements Log
  {
    @Override
    public void trace(String message, Object... args)
    {
    }

    @Override
    public void debug(String message, Object... args)
    {
    }

    @Override
    public void info(String message, Object... args)
    {
    }

    @Override
    public void warn(String message, Object... args)
    {
    }

    @Override
    public void warn(Throwable throwable)
    {
    }

    @Override
    public void error(String message, Object... args)
    {
    }

    @Override
    public void error(Throwable throwable)
    {
    }

    @Override
    public void fatal(String message, Object... args)
    {
    }

    @Override
    public void fatal(Throwable throwable)
    {
    }

    @Override
    public void dump(String message, Throwable throwable)
    {
    }

    @Override
    public void dump(Throwable throwable)
    {
    }
  }
}
