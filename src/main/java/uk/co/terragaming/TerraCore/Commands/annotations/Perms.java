package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import uk.co.terragaming.TerraCore.Commands.Flag;

/**
 * Annotates a {@link Command} method or a {@link Optional} or {@link Flag} with an array of permission requirements.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Perms {
	
	/**
	 * The array of {@link Perm}.
	 * @return The array of {@link Perm}.
	 */
	Perm[] value();
}
