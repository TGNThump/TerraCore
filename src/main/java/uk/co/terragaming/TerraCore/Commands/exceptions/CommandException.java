package uk.co.terragaming.TerraCore.Commands.exceptions;

import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.Util.TextException;


public class CommandException extends TextException{
	
	private static final long serialVersionUID = -1391484198599433287L;

	public CommandException(Text message) {
		super(message);
	}

	public CommandException(String string) {
		super(string);
	}
	
	public CommandException(Text message, Throwable cause){
		super(message, cause);
	}
	
	public CommandException(String string, Throwable cause){
		super(string, cause);
	}
	
}
