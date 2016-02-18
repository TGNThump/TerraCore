package uk.co.terragaming.TerraCore.Foundation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Module{

	String name();
	String version() default "unknown";
	boolean enabled() default true;
	Class<?> parent() default void.class;
	
}
