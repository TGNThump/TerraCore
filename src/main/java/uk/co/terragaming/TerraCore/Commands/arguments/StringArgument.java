package uk.co.terragaming.TerraCore.Commands.arguments;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;


public class StringArgument implements ArgumentParser{

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		if (arg.startsWith("'") || arg.startsWith("\"")) return (T) arg.substring(1, arg.length());
		return (T) arg;
	}
	
	@Override
	public int getArgumentEnd(String arguments){
		if (arguments.startsWith("'")) return arguments.indexOf("'");
		if (arguments.startsWith("\"")) return arguments.indexOf("\"");
		return arguments.indexOf(' ');
	}
	
}
