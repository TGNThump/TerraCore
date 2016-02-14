package uk.co.terragaming.TerraCore.Commands;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.co.terragaming.TerraCore.Util.Conditions.notNull;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder.Literal;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Default;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;

import com.google.common.collect.Lists;

/**
 * A {@link java.lang.reflect.Parameter} wrapper that collects information about a command parameter.
 */
public class Parameter {
	
	private final java.lang.reflect.Parameter parameter;
	private final MethodCommand method;
	
	private String primary;
	private Class<?> type;
	private List<String> alias = Lists.newArrayList();
	private List<String> perms = Lists.newArrayList();
	private Optional<String> desc = Optional.empty();
	private Optional<String> defaultValue = Optional.empty();
	
	private boolean isFlag = false;
	private boolean isOptional = false;
	private boolean isVarArgs = false;
	private boolean valid = true;
	
	public Parameter(MethodCommand method, java.lang.reflect.Parameter parameter){
		checkNotNull(method, "method");
		checkNotNull(parameter, "parameter");
		this.method = method;
		this.parameter = parameter;
		
		Desc desc = parameter.getAnnotation(Desc.class);
		Default defaultValue = parameter.getAnnotation(Default.class);
		Perm[] perms = parameter.getAnnotationsByType(Perm.class);
		Alias[] alias = parameter.getAnnotationsByType(Alias.class);
		
		this.primary = parameter.getName();
		this.type = parameter.getType();
		
		this.isVarArgs = parameter.isVarArgs();
		
		if (this.type.isAssignableFrom(Optional.class)){
			this.isOptional = true;
			this.type = (Class<?>)((ParameterizedType)parameter.getParameterizedType()).getActualTypeArguments()[0];
		}
		
		if (this.type.isAssignableFrom(Flag.class)){
			this.isFlag = true;
			this.type = (Class<?>)((ParameterizedType)parameter.getParameterizedType()).getActualTypeArguments()[0];
		}
		
		if (notNull(desc)) this.desc = Optional.of(desc.value());
		if (notNull(defaultValue)) this.defaultValue = Optional.of(defaultValue.value());
		
		for (int i = 0; i < perms.length; i++){
			this.perms.add(perms[i].value());
		}
		
		for (int i = 0; i < alias.length; i++){
			this.alias.add(alias[i].value());
		}
		
		if (!isFlag && !this.alias.isEmpty()) valid = false;
		if (!isFlag && !this.perms.isEmpty()) valid = false;
		if (!isFlag && !isOptional && this.defaultValue.isPresent()) valid = false;
		if (isFlag && isVarArgs) valid = false;
	
		this.alias.add(primary);
	}
	
	public Text getUsage(CommandSource source){
		if (isOptional()){
			Literal builder = Texts.builder("[" + getPrimary() + (defaultValue.isPresent() ? "=" + defaultValue.get() : "") + "]" + (isVarArgs() ? "..." : ""));
			builder.color(TextColors.AQUA);
			builder.onHover(TextActions.showText(getHoverText(source)));
			return builder.toText();
		} else if (isFlag()){
			if (isBoolean()){
				String text = "[";
				for (int i = 0; i < alias.size(); i++){
					text += "-" + alias.get(i);
					if (i < alias.size() - 1) text += " | ";
				}
				text += "]";
				Literal builder = Texts.builder(text);
				builder.color(TextColors.AQUA);
				builder.onHover(TextActions.showText(getHoverText(source)));
				return builder.toText();
			} else {
				String text = "[";
				for (int i = 0; i < alias.size(); i++){
					text += "-" + alias.get(i) + " value";
					if (i < alias.size() - 1) text += " | ";
				}
				text += "]";
				Literal builder = Texts.builder(text);
				builder.color(TextColors.AQUA);
				builder.onHover(TextActions.showText(getHoverText(source)));
				return builder.toText();
			}
		} else {
			Literal builder = Texts.builder("<" + getPrimary() + ">" + (isVarArgs() ? "..." : ""));
			builder.color(TextColors.GREEN);
			builder.onHover(TextActions.showText(getHoverText(source)));
			return builder.toText();
		}
	}
	
	private Text getHoverText(CommandSource source){
		Literal builder = Texts.builder();
		
		builder.append(
			Texts.of(TextColors.AQUA, type.getSimpleName()),
			Texts.of(" | "),
			Texts.of(TextColors.AQUA, (isOptional()? "Optional" : (isFlag() ? "Flag" : "Required"))),
			Texts.of("\n")
		);
		
		if (desc.isPresent()){
			builder.append(Texts.of(TextColors.GRAY, desc.get()));
		}
		
		if (!perms.isEmpty()){
			
			boolean perm = false;
			for (String p : this.perms){
				if (source.hasPermission(p)){
					perm = true;
					break;
				}
			}
			
			builder.append(
				Texts.of("\n\n"),
				Texts.of(perm ? TextColors.GREEN : TextColors.RED, "Requires Permission")
			);
		}
		
		return builder.toText();
	}
	
	public boolean isBoolean(){
		return type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class);
	}
	
	public java.lang.reflect.Parameter getParameter(){
		return parameter;
	}
	
	public MethodCommand getMethod(){
		return method;
	}
	
	public String getPrimary(){
		return primary;
	}
	
	public Class<?> getType(){
		return type;
	}
	
	public boolean isFlag(){
		return isFlag;
	}
	
	public boolean isOptional(){
		return isOptional;
	}
	
	public boolean isVarArgs(){
		return isVarArgs;
	}
	
	public Optional<String> getDesc(){
		return desc;
	}
	
	public Optional<String> getDefault(){
		return defaultValue;
	}
	
	/**
	 * Get a list of all aliases (including the primary) for this parameter.
	 * @return A list of aliases.
	 */
	public List<String> getAliases() {
		return alias;
	}
	
	public List<String> getPerms(){
		return perms;
	}
	
	/**
	 * Evaluates true if the method is a valid command.
	 * @return The validity of the command.
	 */
	public boolean isValid(){
		return valid;
	}
	
}
