package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


public interface ArgumentParser {
	
	/**
	 * Check if this type can be returned by the parser.
	 */
	public boolean isTypeSupported(Class<?> type);
	
	/**
	 * Parses the argument.
	 * @throws ArgumentException If the argument is invalid for that type
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException;
	
	/**
	 * Returns a human readable name for this ArgumentType 
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return type.getSimpleName();
	}
	
	/**
	 * Suggests some arguments that could be used with parseArgument()
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default List<String> suggestArguments(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		return Lists.newArrayList();
	}
	
	/**
	 * This method returns the end-index of the current argument from a string of arguments. (Returns 0 if argument should be an empty string or -1 if everything is used)<br>
	 * By default, this returns the first occurrence of a space char. (one-word-arguments)<br> 
	 * <br>
	 * <code>arguments.indexOf(' ')</code><br>
	 * <br>
	 * But this method can be used to create argument parsers that need more than one word or work with start and end sequences like from <code>"</code> to <code>"</code>
	 */
	public default int getArgumentEnd(String arguments){
		return arguments.indexOf(' ');
	}
	
	/**
	 * This method provides a description used to explain this argument-type.
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default Text getArgumentTypeDescription(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		Text suggs = getSuggestionText(type, "");
		if (suggs.equals(Text.of())) 
			return Text.of(
					TextColors.GREEN, "Type: ", TextColors.WHITE, getArgumentTypeName(type)
					);
		else 
			return Text.of(
					TextColors.GREEN, "Type: ", TextColors.WHITE, getArgumentTypeName(type), "\n",
					getSuggestionText(type, "")
					);
	}
	
	/**
	 * Returns a nice formatted {@link Text} with some suggestions.
	 * Returns an empty {@link Text} if there are no suggestions.
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	default Text getSuggestionText(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		Builder tb = Text.builder();
		
		List<String> suggs = suggestArguments(type, prefix);
		if (suggs.isEmpty()) return Text.of();
		
		for (int i = 0; i < suggs.size(); i++){
			if (i > 4){
				tb.append(Text.of(TextColors.WHITE, "..."));
				break;
			}
			
			if (i != 0) tb.append(Text.of(TextColors.WHITE, ", "));
			tb.append(Text.of(TextColors.GRAY, suggs.get(i)));
		}
		
		return tb.build();
	}
	
	/**
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	default void checkTypeSupported(Class<?> type) throws IllegalArgumentException {
		if (!isTypeSupported(type)) throw new IllegalArgumentException();
	}	
}
