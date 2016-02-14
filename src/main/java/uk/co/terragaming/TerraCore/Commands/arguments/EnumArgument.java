package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import com.google.common.collect.Lists;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;


public class EnumArgument implements ArgumentParser {
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type.isEnum();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		Enum<?>[] values = (Enum<?>[]) type.getEnumConstants();
		for (Enum<?> e : values){
			if (arg.equalsIgnoreCase(e.toString().replace(' ', '_')) || arg.equalsIgnoreCase(e.name())){
				return (T) e;
			}
		}
		
		throw new ArgumentException("Expected a " + getArgumentTypeName(type) + ", got '" + arg + "'");
	}
	
	@Override
	public List<String> suggestArguments(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		List<String> suggestions = Lists.newArrayList();
		try{
			Enum<?>[] values = (Enum<?>[]) type.getEnumConstants();
			for (Enum<?> e : values){
				suggestions.add(e.toString().replace(' ', '_'));
			}
		} catch (Exception ex){}
		
		return suggestions;
	}
}
