package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.AuthorizationException;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class SpeedCommand {
	
	@Command("speed")
	@Desc("Changes the players speed.")
	@Perm("tc.core.speed")
	@Alias("s")
	public CommandResult onSpeed(Context context,
		@Desc("The speed to set") Double speed,
		@Desc("The players who's speed to update.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		Double multiplier = Math.min(speed, 20);
		double flySpeed = 0.05d * multiplier;
		double walkSpeed = 0.1d * multiplier;
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;

				if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get()){
					player.offer(Keys.FLYING_SPEED, flySpeed);
					source.sendMessage(Text.of(TextColors.AQUA, "You set your flying speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
				} else {
					player.offer(Keys.WALKING_SPEED, walkSpeed);
					source.sendMessage(Text.of(TextColors.AQUA, "You set your walking speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get()){
						player.offer(Keys.FLYING_SPEED, flySpeed);
						player.sendMessage(Text.of(TextColors.AQUA, "You set your flying speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
					} else {
						player.offer(Keys.WALKING_SPEED, walkSpeed);
						player.sendMessage(Text.of(TextColors.AQUA, "You set your walking speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
					}
				} else if (player.hasPermission("tc.core.speed.others")){
					if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get()){
						player.offer(Keys.FLYING_SPEED, flySpeed);
						player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, " set your flying speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
						source.sendMessage(Text.of(TextColors.AQUA, "Set ", TextColors.YELLOW, player.getName(), TextColors.AQUA, "s flying speed to to ", TextColors.YELLOW, speed, TextColors.AQUA, "."));
					} else {
						player.offer(Keys.WALKING_SPEED, walkSpeed);
						player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, " set your walking speed to ", TextColors.YELLOW, multiplier, TextColors.AQUA, "."));
						source.sendMessage(Text.of(TextColors.AQUA, "Set ", TextColors.YELLOW, player.getName(), TextColors.AQUA, "s walking speed to to ", TextColors.YELLOW, speed, TextColors.AQUA, "."));
					}
				} else {
					throw new AuthorizationException();
				}
			}
		}
		return CommandResult.success();
	}
}
