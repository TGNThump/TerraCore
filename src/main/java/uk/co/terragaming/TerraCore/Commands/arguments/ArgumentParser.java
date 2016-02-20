package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;

/**
 * A ArgumentParser parses a String argument into an Object.
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public interface ArgumentParser {
	
	/**
	 * Check if this type can be returned by the parser.
	 */
	
	/**
	 * Check if this type is supported by the {@link ArgumentParser}.
	 * @param type The type to check.
	 * @return A boolean representing whether the type is supported.
	 */
	public boolean isTypeSupported(Class<?> type);

	/**
	 * Parse the argument.
	 * @param type The expected type.
	 * @param arg The argument to parse.
	 * @param <T> The type of the argument.
	 * @return The parsed argument cast to the type.
	 * @throws ArgumentException If the argument is invalid for that type.
	 * @throws IllegalArgumentException If the type is not supported by this {@link ArgumentParser}. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException;
	
	/**
	 * Returns a human readable name for the ArgumentType.
	 * @param type The type whose name to get.
	 * @return The name of the type
	 * @throws IllegalArgumentException If the type is not supported by this {@link ArgumentParser}. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return type.getSimpleName();
	}

	/**
	 * Suggests a list of arguments that could be used with parseArgument()
	 * @param type The expected type.
	 * @param prefix The argument prefix to check.
	 * @return A list of suggested arguments.
	 * @throws IllegalArgumentException If the type is not supported by this {@link ArgumentParser}. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		return Lists.newArrayList();
	}
	
	/**
	 * Suggests a list of arguments that begin with the specified argument prefix.
	 * @param type The expected type.
	 * @param prefix The argument prefix.
	 * @return A list of arguments that start with the specified prefix.
	 * @throws IllegalArgumentException If the type is not supported by this {@link ArgumentParser}. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	public default List<String> suggestArgs(Class<?> type, String prefix) throws IllegalArgumentException {
		List<String> ret = Lists.newArrayList();
		if (prefix.isEmpty()) return getAllSuggestions(type, prefix);
		prefix = prefix.toLowerCase();
		for (String sugg : getAllSuggestions(type, prefix)){
			if (sugg.toLowerCase().startsWith(prefix)) ret.add(sugg);
		}
		return ret;
	}
	
	default <T> ArgumentException getArgumentException(Class<T> type, String arg){
		return new ArgumentException(Text.of(TextColors.RED, "Expected a ", TextColors.GRAY, getArgumentTypeName(type), TextColors.RED,  ", got ", TextColors.YELLOW, arg, TextColors.RED, "."), arg, this, type);
	}
	
	/**
	 * This method returns the end-index of the current argument from a string of arguments. (Returns 0 if argument should be an empty string or -1 if everything is used)<br>
	 * By default, this returns the first occurrence of a space char. (one-word-arguments)<br>
	 * <br>
	 * <code>arguments.indexOf(' ')</code><br>
	 * <br>
	 * But this method can be used to create argument parsers that need more than one word or work with start and end sequences like from <code>"</code> to <code>"</code>

	 * @param arguments The string of arguments.
	 * @return The index of the end of the argument.
	 */
	public default int getArgumentEnd(String arguments){
		return arguments.indexOf(' ');
	}
	
	/**
	 * Returns a nice formatted {@link Text} with some suggestions.
	 * Returns an empty {@link Text} if there are no suggestions.
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	
	/**
	 * Get a formatted {@link Text} with some argument suggestions.
	 * @param type The expected argument type.
	 * @param prefix The argument prefix.
	 * @return a formatted {@link Text} with some argument suggestions.
	 * @return an empty {@link Text} if there are no suggestions.
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	default Text getSuggestionText(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		Builder tb = Text.builder();
		
		List<String> suggs = getAllSuggestions(type, prefix);
		if (suggs.isEmpty()) return Text.of();
		
		for (int i = 0; i < suggs.size(); i++){
			if (i > 4){
				tb.append(Text.of(TextColors.WHITE, "..."));
				break;
			}
			
			if (i != 0 && i <= 4 && suggs.size() - i > 1) tb.append(Text.of(TextColors.WHITE, ", "));
			tb.append(Text.of(TextColors.GRAY, suggs.get(i)));
		}
		
		return tb.build();
	}

	/**
	 * Check the provided type is supported by this {@link ArgumentParser}.
	 * @param type The type to check.
	 * @throws IllegalArgumentException If the type is not supported by this parser. <i>({@link #isTypeSupported(Class)} returns <code>false</code> for this type)</i>
	 */
	default void checkTypeSupported(Class<?> type) throws IllegalArgumentException {
		if (!isTypeSupported(type)) throw new IllegalArgumentException();
	}	
}
