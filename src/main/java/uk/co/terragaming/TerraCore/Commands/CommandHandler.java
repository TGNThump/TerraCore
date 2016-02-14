package uk.co.terragaming.TerraCore.Commands;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.arguments.ArgumentParser;
import uk.co.terragaming.TerraCore.Commands.arguments.BooleanArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.CatalogArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.CharArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.EnumArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.NumberArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.ObjectArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.PlayerArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.StringArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.UserArgument;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CommandHandler implements MethodCommandService {
	
	private static final ObjectArgument DEFAULT_ARG_PARSER = new ObjectArgument();
	
	private final TerraPlugin plugin;
	
	private HashMap<String, MethodCommand> commands;
	private Set<String> rootLabels;
	
	private Map<String, List<ArgumentParser>> argumentParsers;
	
	public CommandHandler(TerraPlugin plugin){
		this.plugin = plugin;
		
		commands = Maps.newHashMap();
		rootLabels = new HashSet<String>();
		
		argumentParsers = Maps.newHashMap();
		
		addArgumentParser(plugin, new EnumArgument());
		addArgumentParser(plugin, new CatalogArgument());
		addArgumentParser(plugin, new BooleanArgument());
		addArgumentParser(plugin, new CharArgument());
		addArgumentParser(plugin, new NumberArgument());
		addArgumentParser(plugin, new StringArgument());
		addArgumentParser(plugin, new UserArgument());
		addArgumentParser(plugin, new PlayerArgument());
	}
	
	public MethodCommand getCommand(String path){
		return commands.get(path);
	}
	
	public boolean hasCommand(String parentPath) {
		return commands.containsKey(parentPath);
	}
	
	@Override
	public void registerCommands(Object plugin, Object handler) {
		Plugin p = plugin.getClass().getAnnotation(Plugin.class);
		checkNotNull(p, "plugin");
		
		for (Method m : handler.getClass().getMethods()){
			if (!m.isAnnotationPresent(Command.class)) continue;
			MethodCommand command = new MethodCommand(handler, m, this);
			
			for (String alias : command.getAliases()){
				commands.put(command.getParentPath() + " " + alias, command);
				
				if (!command.hasParent()){
					Sponge.getCommandDispatcher().register(plugin, new SpongeCommandImpl(alias, this), alias);
					rootLabels.add(alias);
				}
			}
		}
	
	}
	
	@Override
	public void addArgumentParser(Object plugin, ArgumentParser parser) {
		Plugin p = plugin.getClass().getAnnotation(Plugin.class);
		checkNotNull(p, "plugin");
		
		List<ArgumentParser> aps = argumentParsers.get(p.id());
		if (aps == null){
			aps = Lists.newArrayList();
			argumentParsers.put(p.id(), aps);
		}
		
		aps.add(parser);
	}
	
	@Override
	public ArgumentParser getArgumentParser(Object plugin, Class<?> type) {
		Plugin p = plugin.getClass().getAnnotation(Plugin.class);
		checkNotNull(p, "plugin");
		
		List<ArgumentParser> aps = argumentParsers.getOrDefault(p.id(), Lists.newArrayList());
		for (int i = aps.size() - 1; i >= 0; i--){
			ArgumentParser ap = aps.get(i);
			if (ap.isTypeSupported(type)) return ap;
		}
		
		p = this.plugin.getClass().getAnnotation(Plugin.class);
		if (p == null) return DEFAULT_ARG_PARSER;
			
			aps = argumentParsers.getOrDefault(p.id(), Lists.newArrayList());
		for (int i = aps.size()-1; i >= 0; i--){
			ArgumentParser ap = aps.get(i);
			if (ap.isTypeSupported(type)) return ap; 
		}
		
		return DEFAULT_ARG_PARSER;
	}


	public CommandResult processCommand(CommandSource source, String string) {

	}


	public Collection<? extends String> getCommandSuggestions(CommandSource source, String string) {

	}


	public boolean anyPermission(CommandSource source, String label) {

	}


	public Text getCommandUsage() {

	}
	
}
