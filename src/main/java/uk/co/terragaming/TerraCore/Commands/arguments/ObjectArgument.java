package uk.co.terragaming.TerraCore.Commands.arguments;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


public class ObjectArgument implements ArgumentParser {
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		try{
			Method m = type.getMethod("fromString", String.class);
			if (Modifier.isStatic(m.getModifiers()) && type.isAssignableFrom(m.getReturnType())){
				return (T) m.invoke(null, arg);
			}
		} catch (Exception ex){}
		
		try{
			return type.getConstructor(String.class).newInstance(arg);
		} catch (Exception ex2){
			throw new ArgumentException(Text.of(TextColors.RED, "Expected a ", TextColors.AQUA, getArgumentTypeName(type), TextColors.RED,  ", got '", TextColors.LIGHT_PURPLE, arg, TextColors.RED, "'"), arg, this, type);
		}
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
	
		List<String> suggestions = Lists.newArrayList();
		try{
		 	Method m = type.getMethod("values");
		 	if (Modifier.isStatic(m.getModifiers()) && Collection.class.isAssignableFrom(m.getReturnType())){
		 		Collection<?> values = (Collection<?>) m.invoke(null);
		 		if (values != null){
		 			for (Object o : values){
		 				if (o != null){
		 					suggestions.add((String) o);
		 				}
		 			}
		 		}
		 	}
		} catch (Exception ex){}
		return suggestions;
	}
}
