package uk.co.terragaming.TerraCore.Commands;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

public class SpongeCommandImpl implements CommandCallable{
	
	private String label;
	private CommandHandler commandHandler;
	
	public SpongeCommandImpl(String label, CommandHandler ch){
		this.label = label;
		this.commandHandler = ch;
	}

	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		return commandHandler.processCommand(source, label + " " + arguments);
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
		List<String> suggestions = Lists.newArrayList();
		suggestions.addAll(commandHandler.getCommandSuggestions(source, label + " " + arguments));
		return suggestions;
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return commandHandler.anyPermission(source, label);
	}

	@Override
	public Optional<? extends Text> getShortDescription(CommandSource source) {
		return Optional.empty();
	}

	@Override
	public Optional<? extends Text> getHelp(CommandSource source) {
		return Optional.empty();
	}

	@Override
	public Text getUsage(CommandSource source) {
		return commandHandler.getCommandUsage(source, label);
	}

	
}
