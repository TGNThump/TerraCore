package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import org.spongepowered.api.command.CommandResult;

import uk.co.terragaming.TerraCore.Commands.Flag;
import uk.co.terragaming.TerraCore.Util.Context;

/**
 * Annotates a method as a command.
 * A method annotated as a command must return a {@link CommandResult} and have a {@link Context} as the first parameter.
 * 
 * <p>Command Methods can be annotated with different annotations to assign them settings.<br>
 * {@link Alias} can be used to add an alias to the command.<br>
 * {@link Desc} can be used to set the description of the command.<br>
 * {@link Help} can be used to set the help message for the command.<br>
 * {@link Perm} can be used to add a permission requirement to the command.<br>
 * {@link Usage} can be used to override the automatically generated usage message.<br>
 * </p>
 * 
 * <p>Command Parameters can be annotated with different annotations to assign them settings.<br>
 * {@link Alias} can be used to add an alias to a {@link Flag} parameter.<br>
 * {@link Default} can be used to specify a default value for help messages.<br>
 * {@link Desc} can be used to set a description for the parameter.<br>
 * {@link Perm} can be used to add a permission requirement to a {@link Flag} or {@link Optional} parameter.
 * </p>
 * 
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	
	/**
     * The path to the command.
     * @return The path to the command.
     */
	String value();
}
