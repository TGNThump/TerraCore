package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class InvseeCommand {
	
	@Command("invsee")
	@Desc("Look at the target players inventory.")
	@Perm("tc.core.invsee")
	public CommandResult onInvsee(Context context,
		@Desc("The player whos inventory to view.") Player target
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			Player player = (Player) source;
			player.openInventory(target.getInventory());
			
			source.sendMessage(Text.of(TextColors.AQUA, "You looked at ", TextColors.YELLOW, target.getName(), TextColors.AQUA, "s inventory."));
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
}
