package uk.co.terragaming.TerraEssentials.commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.LocatedSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraCore.Util.WorldTime;


public class TimeCommand {
	
	@Command("time")
	@Desc("Get the time.")
	@Perm("tc.core.time")
	public CommandResult onWeather(Context context, 
			@Desc("The time to set.") Optional<WorldTime> time) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof LocatedSource){
			LocatedSource locSource = (LocatedSource) source;
			
			if (!time.isPresent()){
				WorldTime local = new WorldTime(((LocatedSource) source).getWorld().getProperties().getWorldTime());
				locSource.sendMessage(Text.of(TextColors.AQUA, "The current time is ", TextColors.YELLOW, local.get12Hour(), TextColors.AQUA, "."));
			} else {
				locSource.getWorld().getProperties().setWorldTime(time.get().getTickTime());
				locSource.sendMessage(Text.of(TextColors.AQUA, "You set the time to ", TextColors.YELLOW, time.get().get12Hour(), TextColors.AQUA, "."));
			}
			
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
	
}
