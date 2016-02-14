package uk.co.terragaming.TerraCore.Commands.exceptions;

import javax.annotation.Nullable;

import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.Commands.Parameter;


public class ArgumentException extends CommandException{
	
	private static final long serialVersionUID = -653709344998083527L;
	
	@Nullable
	private final Parameter parameter;
	
	public ArgumentException(Text message) {
		super(message);
		this.parameter = null;
	}

	public ArgumentException(String string) {
		super(string);
		this.parameter = null;
	}
	
	public ArgumentException(Text message, @Nullable Parameter parameter){
		super(message);
		this.parameter = parameter;
	}
	
	public ArgumentException(String string, @Nullable Parameter parameter){
		super(string);
		this.parameter = parameter;
	}
	
	@Nullable
	public Parameter getParameter(){
		return parameter;
	}
	
}
