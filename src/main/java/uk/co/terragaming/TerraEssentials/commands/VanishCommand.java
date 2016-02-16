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


public class VanishCommand {
	
	@Command("vanish")
	@Desc("Vanish the target player.")
	@Perm("tc.core.vanish")
	@Alias("v")
	public CommandResult onClear(Context context,
		@Desc("The player to toggle visability.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				Player player = (Player) source;

				if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, true);
					player.sendMessage(Text.of(TextColors.AQUA, "You are now invisible."));
				}
				else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, false);
					player.sendMessage(Text.of(TextColors.AQUA, "You are now visible."));
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				if (player.equals(source)){
					if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
					{
						player.offer(Keys.INVISIBLE, true);
						player.sendMessage(Text.of(TextColors.AQUA, "You are now invisible."));
					}
					else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
					{
						player.offer(Keys.INVISIBLE, false);
						player.sendMessage(Text.of(TextColors.AQUA, "You are now visible."));
					}
				} else if (player.hasPermission("tc.core.vanish.others")){
					if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
					{
						player.offer(Keys.INVISIBLE, true);
						player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, " has made you invisible."));
						source.sendMessage(Text.of(TextColors.AQUA, "You made ", TextColors.YELLOW, source.getName(), TextColors.AQUA, " invisible."));
					}
					else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
					{
						player.offer(Keys.INVISIBLE, false);
						player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, " has made you visible."));
						source.sendMessage(Text.of(TextColors.AQUA, "You made ", TextColors.YELLOW, source.getName(), TextColors.AQUA, " visible."));
					}
					
					
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
