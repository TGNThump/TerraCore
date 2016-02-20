package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.co.terragaming.TerraCore.Commands.Flag;

/**
 * Annotates a {@link Command} or {@link Flag} Parameter to with an array of aliases.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Aliases {
	
	/**
	 * The array of aliases attached to the method or parameter.
	 * @return The array of aliases.
	 */
	Alias[] value();
}
