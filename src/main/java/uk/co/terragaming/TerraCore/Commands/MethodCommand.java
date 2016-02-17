package uk.co.terragaming.TerraCore.Commands;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.co.terragaming.TerraCore.Util.Conditions.notNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Help;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.annotations.Usage;
import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;
import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraCore.Util.Text.MyText;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * A {@link Method} wrapper that collects information about a command method.
 */
public class MethodCommand {
	
	private final Object handler;
	private final Method method;
	
	private final CommandHandler commandHandler;
	
	private String[] path;
	
	private String primary;
	private Optional<MethodCommand> parent = Optional.empty();
	private HashMap<String, MethodCommand> children = Maps.newHashMap();
	private List<String> alias = Lists.newArrayList();
	private List<String> perms = Lists.newArrayList();
	private Optional<String> desc = Optional.empty();
	private Optional<String> help = Optional.empty();
	private Optional<String> usage = Optional.empty();
	
	private List<Parameter> params = Lists.newArrayList();
	private HashMap<String, Parameter> flags = Maps.newHashMap();
		
	public MethodCommand(Object handler, Method method, CommandHandler commandHandler){
		checkNotNull(handler, "handler");
		checkNotNull(method, "method");
		checkNotNull(commandHandler, "commandHandler");
		this.handler = handler;
		this.method = method;
		this.commandHandler = commandHandler;
		
		Command definition = method.getAnnotation(Command.class);
		if (definition == null || !method.getReturnType().isAssignableFrom(CommandResult.class)){
			throw new IllegalArgumentException();
		}
		
		Desc desc = method.getAnnotation(Desc.class);
		Help help = method.getAnnotation(Help.class);
		Usage usage = method.getAnnotation(Usage.class);
		Perm[] perms = method.getAnnotationsByType(Perm.class);
		Alias[] alias = method.getAnnotationsByType(Alias.class);
		
		this.path = definition.value().split(" ", -1);
		this.primary = path[path.length - 1];
		this.alias.add(primary);
		
		String parentPath = getParentPath();
		
		if (!parentPath.isEmpty()){
			if (commandHandler.hasCommand(parentPath)){
				MethodCommand parent = commandHandler.getCommand(parentPath);
				this.setParent(parent);
			} else {
				throw new InvalidParameterException("Command '" + method.toString() + "' Registered before Parent.");
			}
		}
		
		if (notNull(desc)) this.desc = Optional.of(desc.value());
		if (notNull(help)) this.help = Optional.of(help.value());
		if (notNull(usage)) this.usage = Optional.of(usage.value());
		
		for (int i = 0; i < perms.length; i++){
			this.perms.add(perms[i].value());
		}
		
		for (int i = 0; i < alias.length; i++){
			this.alias.add(alias[i].value());
		}
		
		if (!method.getParameters()[0].getType().isAssignableFrom(Context.class)){
			throw new IllegalArgumentException();
		}
		
		for (java.lang.reflect.Parameter param : method.getParameters()){
			if (param.getType().isAssignableFrom(Context.class)) continue;
			Parameter parameter = new Parameter(this, commandHandler, param);
			
			if (!parameter.isValid()) throw new IllegalArgumentException();
			
			this.params.add(parameter);
			if (parameter.isFlag()){
				parameter.getAliases().forEach(a -> {
					this.flags.put(a, parameter);
				});
			}
		}
	}
	
	public List<String> getSuggestions(CommandSource source, String args){

		List<String> suggestions = Lists.newArrayList();
		
		try{
			parseArgs(source, args);
		} catch (ArgumentException e){
			while (e != null){
				if (e instanceof ArgumentException.NotEnoughArgumentsException){
					ArgumentParser ap = commandHandler.getArgumentParser(e.getExpectedType());
					if (args.endsWith(" ") || args.isEmpty()) suggestions.addAll(ap.suggestArgs(e.getExpectedType(), ""));
				} else if (e instanceof ArgumentException.TooManyArgumentsException){
				} else {
					ArgumentParser ap = commandHandler.getArgumentParser(e.getExpectedType());
					for (String s : ap.suggestArgs(e.getExpectedType(), e.getWrongArgument())){
						suggestions.add(s);
					}
				}
				
				e = e.getAnother();
			}
		} finally {
			params.forEach(p -> {p.resetValue();});
		}
		
		if (suggestions.size() > 20) suggestions.subList(20, suggestions.size()).clear();
		return suggestions;
		
	}
	
	public CommandResult execute(CommandSource source, String args) throws ArgumentException{
		List<Object> cmdParams;
		try {
			cmdParams = parseArgs(source, args);
			params.forEach(p -> {p.resetValue();});
		} catch (ArgumentException ex) {
			params.forEach(p -> {p.resetValue();});
			throw ex;
		}
		
		boolean p = false;
		for (String perm : getPerms()){
			if (source.hasPermission(perm)) p = true;
		}
		
		if (!p){
			source.sendMessage(Text.of(TextColors.RED, "You do not have permission to execute this command!"));
			return CommandResult.empty();
		}
		
		
		
		try {
			return (CommandResult) method.invoke(handler, cmdParams.toArray());
		} catch (Exception e){
			if (e instanceof InvocationTargetException){
				Throwable c = e.getCause();
				if (c == null) c = e;
				
				Text.Builder builder = Text.builder();
				builder.append(Text.of("Error: "));
				
				StringWriter sw = new StringWriter();
				c.printStackTrace(new PrintWriter(sw));
				builder.append(Text.of(sw.toString()));
				
				
				source.sendMessage(Text.of(TextColors.RED, TextActions.showText(builder.build()), "An error occoured while trying to execute this command."));
				c.printStackTrace();
			} else {
				
				Text.Builder builder = Text.builder();
				builder.append(Text.of("Error: "));
				
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				builder.append(Text.of(sw.toString()));
				
				source.sendMessage(Text.of(TextColors.RED, TextActions.showText(builder.build()), "An error occoured while trying to execute this command."));
				e.printStackTrace();
			}			
		}
		return CommandResult.empty();
	}
	
	private List<Object> parseArgs(CommandSource source, String args) throws ArgumentException{		
		List<Object> parsedArgs = Lists.newArrayList();
		Context context = new Context();
		context.put(CommandSource.class, source);
		parsedArgs.add(context);
			
		for (Parameter param : getParameters()){
			if (param.isFlag()) continue;
			
			ArgumentParser ap = commandHandler.getArgumentParser(param.getType());
			
			while (true){
				while (args.startsWith(" ")) args = args.substring(1);
				
				if (args.startsWith("-")){
					String flag;
					if (args.indexOf(" ") > 0){
						flag = args.substring(1, args.indexOf(" "));
						args = args.substring(args.indexOf(" "));
					} else {
						flag = args.substring(1);
						args = "";
					}

					if (flags.containsKey(flag)){
						args = flags.get(flag).parse(source, args);
					} else throw new ArgumentException(Text.of(TextColors.RED, "'" + flag + "' is not a valid flag for this command."), flag, null, null);
					continue;
				}
				
				if (args.isEmpty()){
					if (!param.isOptional()){
						throw new ArgumentException.NotEnoughArgumentsException(Text.of(TextColors.RED, "Not enough arguments!"), ap, param.getType());
					}
					break;
				}
				
				args = param.parse(source, args);
				if (!param.isVarArgs()) break;
			}
		}
		
		while (!args.isEmpty()){
			while (args.startsWith(" ")) args = args.substring(1);
			
			if (args.startsWith("-")){
				String flag;
				if (args.indexOf(" ") > 0){
					flag = args.substring(1, args.indexOf(" "));
					args = args.substring(args.indexOf(" "));
				} else {
					flag = args.substring(1);
					args = "";
				}

				if (flags.containsKey(flag)){
					args = flags.get(flag).parse(source, args);
				} else throw new ArgumentException(Text.of(TextColors.RED, "'" + flag + "' is not a valid flag for this command."), flag, null, null);
				continue;
			}
			
			throw new ArgumentException.TooManyArgumentsException(Text.of(TextColors.RED, "Too many arguments!"), args);
		}
		
		for (Parameter param : getParameters()){
			parsedArgs.add(param.getValue());
		}
		
		return parsedArgs;
	}
	
	/**
	 * The usage override for this command.
	 * @return The usage override
	 */
	public Optional<String> getUsageOverride() {
		return usage;
	}
	
	public Text getUsage(CommandSource source){
		if (usage.isPresent()) return Text.of(TextColors.YELLOW, usage.get());
		
		Builder builder = Text
				.builder("/")
				.color(TextColors.DARK_GRAY)
				.append(getUsagePart(source));
		
		boolean first = true;
		for (Parameter param : getParameters()){
			if (first) first = false;
			else builder.append(Text.of(" "));
			builder.append(param.getUsage(source));
		}
		
		return builder.toText();
	}
	
	protected Text getUsagePart(CommandSource source){
		Builder builder = Text.builder();
		if (this.parent.isPresent()) builder.append(parent.get().getUsagePart(source));
		
		builder.append(Text
				.builder(primary)
				.color(TextColors.DARK_GRAY)
				.onClick(TextActions.suggestCommand("/" + MyText.implode(path, " ") + " "))
				.onHover(TextActions.showText(getUsageHover(source)))
				.toText()
			);
		
		builder.append(Text.of(" "));
		
		return builder.toText();
	}
	
	protected Text getUsageHover(CommandSource source){
		Builder builder = Text.builder();
		
		for (int i = 0; i < alias.size(); i++){
			builder.append(Text.of(TextColors.YELLOW, alias.get(i)));
			if (i < alias.size() - 1) builder.append(Text.of(" | "));
		}
		
		if (desc.isPresent()){
			builder.append(Text.of("\n"));
			builder.append(Text.of(TextColors.GRAY, desc.get()));
		}
		
		return builder.toText();
	}
	
	public int getDepth(){
		if (!parent.isPresent()) return 0;
		return (parent.get().getDepth() + 1);
	}
	
	public MethodCommand getChild(String[] args){
		if (children.containsKey(args[0])){
			return children.get(args[0]).getChild(Arrays.copyOf(args, args.length-1));
		}
		return this;
	}
	
	protected void registerChild(MethodCommand child){
		checkNotNull(child);
		for (String a: child.getAliases()){
			children.put(a, child);
		}
	}
	
	protected void removeChild(MethodCommand child){
		checkNotNull(child);
		children.remove(child);
	}
	
	public MethodCommand setParent(MethodCommand parent){
		if (this.parent.isPresent()) this.parent.get().removeChild(this);
		this.parent = Optional.of(parent);
		parent.registerChild(this);
		return this;
	}
	
	/**
	 * Get the {@link Method} this object wraps.
	 * @return The wrapped method
	 */
	public Method getMethod() {
		return method;
	}
	
	/**
	 * Get a list of {@link Parameter}s for this method.
	 * @return A list of the methods parameters.
	 */
	public List<Parameter> getParameters() {
		return params;
	}
	
	/**
	 * Get a map of strings to flag {@link Parameter}s for this method.
	 * @return A map of strings to flag parameters.
	 */
	public Map<String, Parameter> getFlags() {
		return flags;
	}
	
	/**
	 * Evaluates whether a flag is present matching a key.
	 * @return Whether the flag is present.
	 */
	public boolean hasFlag(String key){
		return flags.containsKey(key);
	}
	
	/**
	 * Get the path of this command.
	 * @return The command path
	 */
	public String[] getPath() {
		return path;
	}
	
	public boolean hasParent(){
		return parent.isPresent();
	}

	/**
	 * Get the primary alias of this command.
	 * @return The primary alias
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * Get a list of all aliases (including the primary) for this command.
	 * @return A list of aliases.
	 */
	public List<String> getAliases() {
		return alias;
	}

	/**
	 * Get a list of permissions required to execute this command.
	 * @return A list of permissions.
	 */
	public List<String> getPerms() {
		return perms;
	}

	/**
	 * The description of this command.
	 * @return The description
	 */
	public Optional<String> getDesc() {
		return desc;
	}

	/**
	 * The help message for this command.
	 * @return The help message
	 */
	public Optional<String> getHelp() {
		return help;
	}
	
	public String getParentPath(){
		String path = MyText.implode(this.path, " ");
		if (path.contains(" "))
			return path.substring(0, path.lastIndexOf(" "));
		return "";
	}
	
	public List<String> getAliasPaths(){
		List<String> paths = Lists.newArrayList();
		if (parent.isPresent()){
			for (String start : parent.get().getAliasPaths()){
				for (String end : getAliases()){
					paths.add(start + " " + end);
				}
			}
		} else {
			for (String end : getAliases()){
				paths.add(end);
			}
		}
		
		return paths;
	}
}
