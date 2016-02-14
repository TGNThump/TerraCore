package uk.co.terragaming.TerraCore.Util;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;


public class TextException extends TextMessageException{

	private static final long serialVersionUID = 3457502519010238333L;
	
	public TextException(Text message) {
		super(Texts.of(TextColors.RED, message));
	}

	public TextException(String string) {
		super(Texts.of(TextColors.RED, string));
	}
	
	public TextException(Text message, Throwable cause){
		super(Texts.of(TextColors.RED, message), cause);
	}
	
	public TextException(String string, Throwable cause){
		super(Texts.of(TextColors.RED, string), cause);
	}
	
}
