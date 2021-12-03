package js.injector;

import javax.inject.Provider;

/**
 * A scope has a cache and controls instances life span. It is applied as a decorator on provisioning provider; remember
 * that a provisioning provider always creates a new instance.
 * 
 * @author Iulian Rotaru
 * @param <T> generic instance type.
 */
public interface IScope<T>
{

  /**
   * Create a scope provider by decorating given provisioning provider.
   * 
   * @param key instance key,
   * @param provisioningProvider provisioning provider, delegated for actual instances creation.
   * @return scope provider.
   */
  Provider<T> scope(Key<T> key, Provider<T> provisioningProvider);

}
