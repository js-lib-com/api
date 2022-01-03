package js.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.inject.Scope;

@Scope
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadScoped {

}
