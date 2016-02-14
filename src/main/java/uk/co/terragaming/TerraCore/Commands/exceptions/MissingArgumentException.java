package uk.co.terragaming.TerraCore.Commands.exceptions;

import javax.annotation.Nullable;

import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.Commands.Parameter;


public class MissingArgumentException extends ArgumentException{
	
	private static final long serialVersionUID = 2778303591906988916L;
	
	public MissingArgumentException(){
		super("Missing Argument");
	}
	
	public MissingArgumentException(Text message) {
		super(message);
	}

	public MissingArgumentException(String string) {
		super(string);
	}
	
	public MissingArgumentException(Text message, @Nullable Parameter parameter){
		super(message, parameter);
	}
	
	public MissingArgumentException(String string, @Nullable Parameter parameter){
		super(string, parameter);
	}
	
}
