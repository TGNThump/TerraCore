package uk.co.terragaming.TerraCore.Commands.arguments;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;


public class CharArgument implements ArgumentParser {

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == Character.class || type == char.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		if (arg.length() == 1){
			return (T) new Character(arg.charAt(0));
		}
		
		throw new ArgumentException("Expected a Character, got '" + arg + "'");
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Character";
	}
}
