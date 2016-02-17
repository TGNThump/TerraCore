package uk.co.terragaming.TerraEssentials.commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.LocatedSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.weather.Weather;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class WeatherCommand {
	
	@Command("weather")
	@Desc("Set the weather.")
	@Perm("tc.core.weather")
	public CommandResult onWeather(Context context,
			@Desc("The weather to set.") Weather weather,
			@Desc("The duration to forecast.") Optional<Long> duration
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof LocatedSource){
			LocatedSource locSource = (LocatedSource) source;
			
			if (duration.isPresent()){
				locSource.getWorld().forecast(weather, duration.get());
			} else {
				locSource.getWorld().forecast(weather);
			}
			
			if (source instanceof Player){
				Player player = (Player) source;
				player.sendMessage(Text.of(TextColors.AQUA, "Set the weather to ", TextColors.YELLOW, weather.getName(), TextColors.AQUA, "."));
			}
			
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
}
