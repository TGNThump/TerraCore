package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a {@link Command} with a Usage.
 * This overrides the Usage that is automatically generated based on the arguments.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Usage {
	
	/**
     * Overrides the Usage of a command.
     * @return Usage instructions for a command
     */
	String value();
}
