package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import uk.co.terragaming.TerraCore.Commands.Flag;

/**
 * Annotates a {@link Command} method or a {@link Optional} or {@link Flag} with a permission requirement.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(Perms.class)
public @interface Perm {
	
	/**
	 * The required permission.
	 * @return The required permission.
	 */
	String value();
}
