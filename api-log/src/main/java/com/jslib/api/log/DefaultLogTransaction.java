package com.jslib.api.log;

public class DefaultLogTransaction
{
  public static final LogTransaction instance = new LogTransaction()
  {
    @Override
    public void beginTransaction()
    {
    }

    @Override
    public void commitTransaction()
    {
    }

    @Override
    public void rollbackTransaction()
    {
    }
  };
}
