package uk.co.terragaming.TerraCore.Commands.exceptions;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class AuthorizationException extends CommandException{

	private static final long serialVersionUID = -5038624448617531757L;

	public AuthorizationException(){
		super(Texts.of(TextColors.RED, "You do not have the required permissions to use that command!"));
	}
	
	public AuthorizationException(Text message) {
		super(message);
	}
	
}
