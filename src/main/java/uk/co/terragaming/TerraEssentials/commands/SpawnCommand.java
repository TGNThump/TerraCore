package uk.co.terragaming.TerraEssentials.commands;

import javax.inject.Inject;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.LocatedSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.Flag;
import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraEssentials.config.EssentialsData;


public class SpawnCommand {
	
	@Inject
	EssentialsData data;
	
	@Command("spawn")
	@Desc("Teleport to spawn.")
	@Perm("tc.core.spawn")
	public CommandResult onSpawn(Context context,
		@Desc("Force the teleport if unsafe.") @Perm("tc.core.spawn.unsafe") @Alias("-f") Flag<Boolean> force	
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			Player player = (Player) source;
			
			if (force.isPresent()){
				player.setLocation(data.spawn.get());
				source.sendMessage(Text.of(TextColors.AQUA, "Teleported you to spawn."));
			} else {
				if (player.setLocationSafely(data.spawn.get())){
					source.sendMessage(Text.of(TextColors.AQUA, "Teleported you to spawn."));
				} else {
					source.sendMessage(Text.of(TextColors.RED, "Could not safely teleport you to spawn."));
					if (source.hasPermission("tc.core.spawn.unsafe"))
						source.sendMessage(Text.of(TextColors.RED, "Use the ", TextColors.YELLOW, "-force", TextColors.RED, " flag to continue anyway."));
				}
			}

			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
	
	@Command("setspawn")
	@Desc("Set the spawn point.")
	@Perm("tc.core.spawn.set")
	@Alias("spawn set")
	public CommandResult onSpawnSet(Context context){
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof LocatedSource){
			data.spawn.set(((LocatedSource) source).getLocation());
			((LocatedSource) source).getWorld().getProperties().setSpawnPosition(((LocatedSource) source).getLocation().getBlockPosition());
			source.sendMessage(Text.of(TextColors.AQUA, "The spawn point has been set to your location."));
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
}
