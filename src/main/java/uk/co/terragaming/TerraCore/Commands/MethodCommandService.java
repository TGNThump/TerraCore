package uk.co.terragaming.TerraCore.Commands;

import org.spongepowered.api.plugin.Plugin;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;

/**
 * A service that provides the ability to register methods in an object as {@link Command}.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public interface MethodCommandService {
	
	/**
	 * Registers all {@link Command} methods in this instance.
	 * @param plugin The {@link Plugin} to register the commands to.
	 * @param handler The method to register.
	 * @throws IllegalArgumentException if the parameter "plugin" is not a {@link Plugin}!
	 */
	public void registerCommands(Object plugin, Object handler) throws IllegalArgumentException;

	/**
	 * Registers an {@link ArgumentParser}, later added parsers with have a higher priority.
	 * @param plugin The {@link Plugin} to register the commands to.
	 * @param parser The {@link ArgumentParser} to register.
	 * @throws IllegalArgumentException if the parameter "plugin" is not a {@link Plugin}!
	 */
	public void addArgumentParser(Object plugin, ArgumentParser parser) throws IllegalArgumentException;
	
	/**
	 * Returns the highest priority {@link ArgumentParser} that supports this class-type.
	 * @param type The argument type.
	 * @return The highest priority {@link ArgumentParser} that supports this class-type.
	 * @throws IllegalArgumentException if the parameter "plugin" is no {@link Plugin}!
	 */
	public ArgumentParser getArgumentParser(Class<?> type) throws IllegalArgumentException;
	
}
