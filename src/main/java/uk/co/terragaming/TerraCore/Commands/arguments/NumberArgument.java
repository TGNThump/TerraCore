package uk.co.terragaming.TerraCore.Commands.arguments;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;


public class NumberArgument implements ArgumentParser {
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return 	type == byte.class || type == Byte.class ||
				type == short.class || type == Short.class ||
				type == int.class || type == Integer.class ||
				type == long.class || type == Long.class ||
				type == float.class || type == Float.class ||
				type == double.class || type == Double.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		if (type == byte.class || type == Byte.class) try { return (T) new Byte(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		if (type == short.class || type == Short.class) try { return (T) new Short(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		if (type == int.class || type == Integer.class) try { return (T) new Integer(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		if (type == long.class || type == Long.class) try { return (T) new Long(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		if (type == float.class || type == Float.class) try { return (T) new Float(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		if (type == double.class || type == Double.class) try { return (T) new Double(arg); } catch (NumberFormatException ex){ throwException(type, arg); }
		
		throw new IllegalArgumentException();
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Number (" + type.getSimpleName() + ")";
	}
	
	public <T> void throwException(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException{
		throw new ArgumentException(Text.of(TextColors.RED, "Expected a ", TextColors.AQUA, getArgumentTypeName(type), TextColors.RED,  ", got '", TextColors.LIGHT_PURPLE, arg, TextColors.RED, "'"), arg, this, type);
	}
}
