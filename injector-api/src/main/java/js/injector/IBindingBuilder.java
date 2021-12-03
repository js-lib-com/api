package js.injector;

import java.lang.annotation.Annotation;
import java.net.URI;

import javax.inject.Provider;

/**
 * Chained builder used by module configuration to collect binding parameters.
 * 
 * @author Iulian Rotaru
 */
public interface IBindingBuilder<T>
{

  default IBindingBuilder<T> annotatedWith(Annotation qualifier)
  {
    return with(qualifier);
  }

  default IBindingBuilder<T> annotatedWith(Class<? extends Annotation> qualifierType)
  {
    return with(qualifierType);
  }

  IBindingBuilder<T> with(Annotation qualifier);

  IBindingBuilder<T> with(Class<? extends Annotation> qualifierType);

  IBindingBuilder<T> named(String name);

  IBindingBuilder<T> to(Class<? extends T> type);

  default IBindingBuilder<T> toInstance(T instance)
  {
    return instance(instance);
  }

  IBindingBuilder<T> instance(T instance);

  default IBindingBuilder<T> toProvider(Provider<T> provider)
  {
    return provider(provider);
  }

  IBindingBuilder<T> provider(Provider<T> provider);

  default IBindingBuilder<T> toProvider(ITypedProvider<T> provider)
  {
    return provider(provider);
  }

  IBindingBuilder<T> provider(ITypedProvider<T> provider);

  IBindingBuilder<T> service();

  IBindingBuilder<T> on(URI implementationURL);

  IBindingBuilder<T> on(String implementationURL);

  IBindingBuilder<T> in(Class<? extends Annotation> scopeType);

  Provider<T> getProvider();

  IBinding<T> getBinding();

  default IBindingBuilder<T> build()
  {
    return this;
  }

}
