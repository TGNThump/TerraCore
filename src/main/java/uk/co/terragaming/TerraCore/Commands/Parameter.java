package uk.co.terragaming.TerraCore.Commands;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.co.terragaming.TerraCore.Util.Conditions.notNull;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Default;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;
import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;

/**
 * A {@link java.lang.reflect.Parameter} wrapper that collects information about a command parameter.
 */
public class Parameter {
	
	private final java.lang.reflect.Parameter parameter;
	private final MethodCommand method;
	private final ArgumentParser ap;
	
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
	
	private Object value = defaultValue.orElse(null);
	
	@SuppressWarnings("unchecked")
	public String parse(CommandSource source, String args) throws ArgumentException{
		if (isFlag() && isBoolean()){
			value = true;
			return args;
		}
		
		if (isVarArgs()){
			if (args.toLowerCase().startsWith("@a")){
				for (String arg : ap.getAllSuggestions(type, "")){
					((List<Object>) value).add(ap.parseArgument(type, arg));
				}
				return args.substring(2);
			}
			
			if (ap.getArgumentEnd(args) > 0){
				((List<Object>) value).add(ap.parseArgument(type, args.substring(0, ap.getArgumentEnd(args))));
				return args.substring(ap.getArgumentEnd(args));
			} else {
				((List<Object>) value).add(ap.parseArgument(type, args));
				return "";
			}
		}
		
		if (ap.getArgumentEnd(args) > 0){
			value = ap.parseArgument(type, args.substring(0, ap.getArgumentEnd(args)));
			return args.substring(ap.getArgumentEnd(args));
		} else {
			value = ap.parseArgument(type, args);
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getValue(){
		if (isFlag()) return Flag.ofNullable(value);
		
		if (isVarArgs()){
			List<Object> list = ((List<Object>) value);
			Object array = Array.newInstance(type, list.size());
			for (int i = 0; i < list.size(); i++){
				Array.set(array, i, list.get(i));
			}
			return array;
		}

		if (isOptional()) return Optional.ofNullable(value);
		return value;
	}
	
	public Parameter(MethodCommand method, CommandHandler handler, java.lang.reflect.Parameter parameter){
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
		
		if (this.isVarArgs){
			this.type = parameter.getType().getComponentType();
			this.value = Lists.newArrayList();
		}
		
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
		
		this.ap = handler.getArgumentParser(this.type);
	}
	
	public Text getUsage(CommandSource source){
		if (isOptional()){
			Builder builder = Text.builder("[" + getPrimary() + (defaultValue.isPresent() ? "=" + defaultValue.get() : "") + "]" + (isVarArgs() ? "..." : ""));
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
				Builder builder = Text.builder(text);
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
				Builder builder = Text.builder(text);
				builder.color(TextColors.AQUA);
				builder.onHover(TextActions.showText(getHoverText(source)));
				return builder.toText();
			}
		} else {
			Builder builder = Text.builder("<" + getPrimary() + ">" + (isVarArgs() ? "..." : ""));
			builder.color(TextColors.GREEN);
			builder.onHover(TextActions.showText(getHoverText(source)));
			return builder.toText();
		}
	}
	
	private Text getHoverText(CommandSource source){
		Builder builder = Text.builder();
		
		builder.append(
			Text.of(TextColors.AQUA, type.getSimpleName()+ (isVarArgs() ? "..." : "")),
			Text.of(" | "),
			Text.of(TextColors.AQUA, (isOptional()? "Optional" : (isFlag() ? "Flag" : "Required"))),
			Text.of("\n")
		);
		
		if (desc.isPresent()){
			builder.append(Text.of(TextColors.GRAY, desc.get()));
		}
		
		Text suggestions = ap.getSuggestionText(type, "");
		
		if (!perms.isEmpty() || !suggestions.isEmpty()){
			builder.append(Text.of("\n\n"));
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
				Text.of(perm ? TextColors.GREEN : TextColors.RED, "Requires Permission")
			);
		}
		
		if (!suggestions.isEmpty()){
			builder.append(Text.of(TextColors.WHITE, "e.g. "));
			builder.append(suggestions);
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
		return (isOptional || isVarArgs());
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
