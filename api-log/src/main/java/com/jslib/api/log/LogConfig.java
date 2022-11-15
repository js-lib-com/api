package com.jslib.api.log;

import java.net.URI;

public interface LogConfig
{
  
  void setServerAddress(URI address);
  
  URI getServerAddress();
  
  void setRootLevel(Level level);
  
  Level getRootLevel();
  
  void setLoggerLevel(String loggerName, Level level);
  
  Level getLoggerLevel(String loggerName);

  void clearLoggerLevel(String loggerName);
  
  void setFilter(String filter);
  
  String getFilter();
  
  void clearFilter();
  
  void commit();
  
}
