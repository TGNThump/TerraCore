package uk.co.terragaming.TerraCore.Commands.exceptions;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;

public class ArgumentException extends CommandException{
	
	private static final long serialVersionUID = -653709344998083527L;
	
	private final String wrongArgument;
	private final ArgumentParser parser;
	private final Class<?> expectedType;
	
	private ArgumentException another;
	
	/**
	 * An exception that represents a wrong argument in a command  
	 * @param message The decorated description of this Exception
	 * @param wrongArgument The argument that caused this error. This is can be an empty string if there is e.g. a command completly missing.
	 * It can not be <code>null</code>
	 * @param parser The ArgumentParser on wich this exception was thrown!
	 * or <code>null</code> if no parser is available. (too many args or such)
	 * @param expectedType The class-type that was expected, where this exception occurred.
	 * or <code>null</code> if nothing is expected (too many args or such)
	 */
	public ArgumentException(Text message, String wrongArgument, ArgumentParser parser, Class<?> expectedType){
		this(message, wrongArgument, parser, expectedType, null);
	}
	
	ArgumentException(Text message, String wrongArgument, ArgumentParser parser, Class<?> expectedType, @Nullable ArgumentException another){
		super(checkNotNull(message));
		this.wrongArgument = checkNotNull(wrongArgument);
		this.parser = parser;
		this.expectedType = expectedType;
		this.another = another;
	}
	
	/**
	 * @return The decorated description of this Exception
	 */
	public Text getDescription(){
		return this.getText();
	}
	
	/**
	 * @return The argument that caused this error. This can be an empty string if there is no arguments.
	 */
	public String getWrongArgument(){
		return wrongArgument;
	}
	
	/**
	 * @return The class type that was expected.
	 */
	public Class<?> getExpectedType(){
		return expectedType;
	}
	
	/**
	 * @return The ArgumentParser on which the exception was thrown.
	 * @return <code>null</code> if no parser is available. (e.g. too many args)
	 */
	public ArgumentParser getParser(){
		return parser;
	}
	
	/**
	 * In some cases there might be more possible argument-fails that got skipped, because there was a default value avaliable.
	 * @return The skipped exception.
	 * @return <code>null</code> if none present.
	 */
	public ArgumentException getAnother(){
		return another;
	}
	
	public ArgumentException setAnother(ArgumentException ex){
		if (ex == null) return this;
		another = ex;
		return this;
	}
	
	public static class NotEnoughArgumentsException extends ArgumentException{
		private static final long serialVersionUID = -2314995517891913565L;
		
		public NotEnoughArgumentsException(Text message, ArgumentParser ap, Class<?> expectedType){ super(message, "", ap, expectedType); }
		public NotEnoughArgumentsException(Text message, ArgumentParser ap, Class<?> expectedType, ArgumentException another){ super(message, "", ap, expectedType, another); }
	}
	
	public static class TooManyArgumentsException extends ArgumentException{
		private static final long serialVersionUID = -3651328015153198614L;

		public TooManyArgumentsException(Text message, String leftArguments){ super(message, leftArguments, null, null); }
		public TooManyArgumentsException(Text message, String leftArguments, ArgumentException another){ super(message, leftArguments, null, null, another); }
	}
	
}
