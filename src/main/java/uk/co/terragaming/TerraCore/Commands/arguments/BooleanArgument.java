package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;

public class BooleanArgument implements ArgumentParser {

	private final static List<String> TRUE_OPTIONS = Lists.newArrayList("y", "yes", "on", "true", "1", "t");
	private final static List<String> FALSE_OPTIONS = Lists.newArrayList("n", "no", "off", "false", "0", "f");
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == Boolean.class || type == boolean.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		String next = arg.toLowerCase();
		if (TRUE_OPTIONS.contains(next)) return (T) Boolean.TRUE;
		if (FALSE_OPTIONS.contains(next)) return (T) Boolean.FALSE;

		throw getArgumentException(type, arg);
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		if (prefix.isEmpty()) return Lists.newArrayList("true", "false");
		else return Lists.newArrayList("true", "false", "yes", "no", "on", "off");
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Boolean";
	}
}
