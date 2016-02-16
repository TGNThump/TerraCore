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


public class HealCommand {
	
	@Command("heal")
	@Desc("Heal the players.")
	@Perm("tc.core.heal")
	public CommandResult onHeal(Context context,
		@Desc("The players to heal.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;
				player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				
				source.sendMessage(Text.of(TextColors.AQUA, "You healed yourself."));
				return CommandResult.success();
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
					
					source.sendMessage(Text.of(TextColors.AQUA, "You healed yourself."));
					return CommandResult.success();
				} else if (player.hasPermission("tc.core.heal.others")){
					player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
					
					source.sendMessage(Text.of(TextColors.AQUA, "You healed ", TextColors.YELLOW, player.getName(), TextColors.AQUA, "."));
					player.sendMessage(Text.of(TextColors.AQUA, "You were healed by  ", TextColors.YELLOW, source.getName(), TextColors.AQUA, "."));
				} else {
					throw new AuthorizationException();
				}
			}
			return CommandResult.success();
		}
	}
	
}
