package uk.co.terragaming.TerraCore.Commands.exceptions;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class InvocationCommandException extends CommandException{

	private static final long serialVersionUID = 6959454170643020091L;

    public InvocationCommandException(String message, Throwable cause) {
        super(Text.of(TextColors.RED, message), checkNotNull(cause));
    }
	
}
