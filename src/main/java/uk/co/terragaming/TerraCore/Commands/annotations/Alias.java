package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.co.terragaming.TerraCore.Commands.Flag;

/**
 * Annotates a {@link Command} or {@link Flag} Parameter to provide it with an alias.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(Aliases.class)
public @interface Alias {

	/**
	 * The alias attached to the method or parameter.
	 * @return The name of the alias.
	 */
	String value();
}
