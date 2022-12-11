package com.jslib.api.log;

public interface LogTransaction
{

  void beginTransaction();

  void commitTransaction();

  void rollbackTransaction();

}
