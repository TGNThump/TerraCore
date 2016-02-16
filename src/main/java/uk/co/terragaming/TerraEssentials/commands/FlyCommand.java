package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.AuthorizationException;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class FlyCommand {
	
	@Command("fly")
	@Desc("Toggles the players ability to fly.")
	@Perm("tc.core.fly")
	public CommandResult onFly(Context context,
		@Desc("The player who's flight to toggle.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;
				boolean canFly = !player.get(Keys.CAN_FLY).get();
				
				player.offer(Keys.IS_FLYING, canFly);
				player.offer(Keys.CAN_FLY, canFly);
				source.sendMessage(Text.of(TextColors.AQUA, "You ", (canFly ? "can now" : "can no longer"), " fly."));
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					boolean canFly = !player.get(Keys.CAN_FLY).get();
					
					player.offer(Keys.IS_FLYING, canFly);
					player.offer(Keys.CAN_FLY, canFly);
					source.sendMessage(Text.of(TextColors.AQUA, "You ", (canFly ? "can now" : "can no longer"), " fly."));
				} else if (player.hasPermission("tc.core.fly.others")){
					boolean canFly = !player.get(Keys.CAN_FLY).get();
					
					player.offer(Keys.IS_FLYING, canFly);
					player.offer(Keys.CAN_FLY, canFly);
					source.sendMessage(Text.of(TextColors.YELLOW, player.getName(), TextColors.AQUA, (canFly ? " can now" : " can no longer"), " fly."));
					player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, (canFly ? " enabled" : " disabled"), " your ability to fly."));
				} else {
					throw new AuthorizationException();
				}
			}
		}
		return CommandResult.success();
	}
}
