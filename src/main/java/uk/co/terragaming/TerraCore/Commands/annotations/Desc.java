package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a {@link Command} or Parameter with a description.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Desc {
		
	/**
     * A description of the command or parameter.
     * @return A description of the command or parameter.
     */
	String value();
}
