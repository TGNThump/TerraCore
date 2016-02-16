package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
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


public class ClearCommand {
	
	@Command("clear")
	@Desc("Clears the players inventory.")
	@Perm("tc.core.clear")
	@Alias("ci") @Alias("clearinventory")
	public CommandResult onClear(Context context,
		@Desc("The player who's inventories to clear.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;

				player.getInventory().clear();
				
				source.sendMessage(Text.of(TextColors.AQUA, "You cleared your inventory."));
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					player.getInventory().clear();
					source.sendMessage(Text.of(TextColors.AQUA, "You cleared your inventory."));
				} else if (player.hasPermission("tc.core.clear.others")){
					player.getInventory().clear();
					source.sendMessage(Text.of(TextColors.YELLOW, player.getName(), TextColors.AQUA, " cleared your inventory."));
					player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, " cleared your inventory."));
				} else {
					throw new AuthorizationException();
				}
			}
		}
		return CommandResult.success();
	}
}
