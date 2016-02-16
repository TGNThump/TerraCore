package uk.co.terragaming.TerraCore;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class CoreCommands {
	
	@Command("gamemode")
	@Desc("Set the players gamemode.")
	@Perm("tc.core.gamemode")
	@Alias("gm")
	public CommandResult onGamemodeCommand(Context context,
		@Desc("The gamemode to set.") GameMode gamemode,
		@Desc("The player who's gamemode to set.") Player... players
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (players.length == 0){
			if (source instanceof Player){
				((Player) source).offer(Keys.GAME_MODE, gamemode);
				source.sendMessage(Text.of(TextColors.AQUA, "Set your gamemode to ", TextColors.YELLOW, gamemode.getName(), TextColors.AQUA, "."));
				return CommandResult.success();
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Please specify at least one player."));
				return CommandResult.empty();
			}
		} else {
			for (Player player : players){
				player.offer(Keys.GAME_MODE, gamemode);
				if (player.equals(source)){
					source.sendMessage(Text.of(TextColors.AQUA, "Set your gamemode to ", TextColors.YELLOW, gamemode.getName(), TextColors.AQUA, "."));
				} else {
					if (player.hasPermission("tc.core.gamemode.others"))
					source.sendMessage(Text.of(TextColors.AQUA, "Set ", TextColors.YELLOW, player.getName(), TextColors.AQUA, " gamemode to ", TextColors.YELLOW, gamemode.getName(), TextColors.AQUA, "."));
					player.sendMessage(Text.of(TextColors.YELLOW, source.getName(), TextColors.AQUA, TextColors.AQUA, " set your gamemode to ", TextColors.YELLOW, gamemode.getName(), TextColors.AQUA, "."));
				}
				return CommandResult.builder().affectedEntities(players.length).build();
			}
		}
		return CommandResult.empty();
	}
	
}
