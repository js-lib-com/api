package js.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.inject.Scope;

/**
 * Mark annotation for instances bound to current thread context. A thread scoped instance is created on the fly and
 * reused from cache as long parent thread is alive.
 * 
 * @author Iulian Rotaru
 */
@Scope
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadScoped {

}
