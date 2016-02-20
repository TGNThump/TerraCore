package uk.co.terragaming.TerraCore.Commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a Parameter with a Default value.
 * <p>This does not do anything but improve the command help messages.</p>
 * 
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Default {

	/**
	 * The default value.
	 * @return The default value.
	 */
	String value();
}
