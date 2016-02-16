package uk.co.terragaming.TerraEssentials.commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.AuthorizationException;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class FeedCommand {
	
	@Command("feed")
	@Desc("Feed the players.")
	@Perm("tc.core.feed")
	public CommandResult onFeed(Context context,
		@Desc("The players to feed.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;
				Optional<FoodData> foodData = player.getOrCreate(FoodData.class);
				
				if (foodData.isPresent()){
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					player.offer(newData);
					
					source.sendMessage(Text.of(TextColors.AQUA, "You feed yourself."));
					return CommandResult.success();
				} else {
					source.sendMessage(Text.of(TextColors.RED, "Player ", TextColors.YELLOW, player.getName(), TextColors.RED, " does not have FoodData."));
					return CommandResult.empty();
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					Optional<FoodData> foodData = player.getOrCreate(FoodData.class);
					
					if (foodData.isPresent()){
						FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
						player.offer(newData);
						
						source.sendMessage(Text.of(TextColors.AQUA, "You feed yourself."));
						return CommandResult.success();
					} else {
						source.sendMessage(Text.of(TextColors.RED, "Player ", TextColors.YELLOW, player.getName(), TextColors.RED, " does not have FoodData."));
						return CommandResult.empty();
					}
				} else if (player.hasPermission("tc.core.heal.others")){
					Optional<FoodData> foodData = player.getOrCreate(FoodData.class);
					
					if (foodData.isPresent()){
						FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
						player.offer(newData);
						
						source.sendMessage(Text.of(TextColors.AQUA, "You feed ", TextColors.YELLOW, player.getName(), TextColors.AQUA, "."));
						player.sendMessage(Text.of(TextColors.AQUA, "You were feed by  ", TextColors.YELLOW, source.getName(), TextColors.AQUA, "."));
						return CommandResult.success();
					} else {
						source.sendMessage(Text.of(TextColors.RED, "Player ", TextColors.YELLOW, player.getName(), TextColors.RED, " does not have FoodData."));
						return CommandResult.empty();
					}
					
				} else {
					throw new AuthorizationException();
				}
			}
			return CommandResult.success();
		}
	}
	
}
