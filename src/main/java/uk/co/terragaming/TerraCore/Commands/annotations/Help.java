package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a {@link Command} with a help message.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Help {
	
	/**
     * The help message of the command.
     * @return The help message of the command.
     */
	String value();
}
