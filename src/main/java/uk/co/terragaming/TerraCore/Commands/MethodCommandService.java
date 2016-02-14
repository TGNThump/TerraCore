package uk.co.terragaming.TerraCore.Commands;

import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;


public interface MethodCommandService {
	
	/**
	 * Registers all {@link Command}-methods in this instance.
	 * @throws IllegalArgumentException if the parameter "plugin" is no {@link Plugin}!
	 */
	public void registerCommands(Object plugin, Object handler);
	
	/**
	 * Adds an {@link ArgumentParser}, later added parsers will have a higher priority.
	 * @throws IllegalArgumentException if the parameter "plugin" is no {@link Plugin}!
	 */
	public void addArgumentParser(Object plugin, ArgumentParser parser);
	
	/**
	 * Returns the priority highest {@link ArgumentParser} that supports this class-type.
	 * @throws IllegalArgumentException if the parameter "plugin" is no {@link Plugin}!
	 */
	public ArgumentParser getArgumentParser(Object plugin, Class<?> type);
	
}
