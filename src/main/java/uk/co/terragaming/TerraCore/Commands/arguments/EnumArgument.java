package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


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
		
		throw new ArgumentException(Text.of(TextColors.RED, "Expected a ", TextColors.AQUA, getArgumentTypeName(type), TextColors.RED,  ", got '", TextColors.LIGHT_PURPLE, arg, TextColors.RED, "'"), arg, this, type);
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
