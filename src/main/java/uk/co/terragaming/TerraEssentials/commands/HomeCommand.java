package uk.co.terragaming.TerraEssentials.commands;

import java.util.Optional;

import javax.inject.Inject;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import uk.co.terragaming.TerraCore.Commands.Flag;
import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraEssentials.config.EssentialsData;
import uk.co.terragaming.TerraEssentials.config.EssentialsData.WorldLocation;


public class HomeCommand {
	
	@Inject
	EssentialsData data;
	
	@Command("home")
	@Desc("Teleport to your home.")
	@Perm("tc.core.home")
	public CommandResult onHome(Context context,
		@Desc("The player who's home to teleport to") @Perm("tc.core.home.others") Optional<Player> player,
		@Desc("Force the teleport if unsafe.") @Perm("tc.core.home.unsafe") @Alias("-f") Flag<Boolean> force
	) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			
			Player target = player.orElse((Player) source);
			
			Player pSource = (Player) source;
			
			if (!data.homes.containsKey(target.getUniqueId())){
				source.sendMessage(Text.of(TextColors.RED, (target.equals(source) ? "You have not set your home." : Text.of(TextColors.YELLOW, target.getName(), TextColors.RED, " has not set their home."))));
				return CommandResult.empty();
			} else {
				Location<World> loc = data.homes.get(target.getUniqueId()).get();
				
				if (force.isPresent()){
					pSource.setLocation(loc);
					source.sendMessage(Text.of(TextColors.AQUA, "Teleported you to ", (target.equals(source) ? "your" : Text.of(TextColors.YELLOW, target.getName(), TextColors.AQUA, "s")), " home."));
				} else {
					if (pSource.setLocationSafely(loc)){
						source.sendMessage(Text.of(TextColors.AQUA, "Teleported you to ", (target.equals(source) ? "your" : Text.of(TextColors.YELLOW, target.getName(), TextColors.AQUA, "s")), " home."));
					} else {
						source.sendMessage(Text.of(TextColors.RED, "Could not safely teleport you to ", (target.equals(source) ? "your" : Text.of(TextColors.YELLOW, target.getName(), TextColors.RED, "s")), " home."));
						if (source.hasPermission("tc.core.spawn.unsafe"))
							source.sendMessage(Text.of(TextColors.RED, "Use the ", TextColors.YELLOW, "-force", TextColors.RED, " flag to continue anyway."));
					}
				}
				return CommandResult.success();
			}
			
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
	
	@Command("sethome")
	@Desc("Set your home.")
	@Perm("tc.core.home.set")
	@Alias("home set")
	public CommandResult onHomeSet(Context context){
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			Player player = (Player) source;			
			
			data.homes.put(player.getUniqueId(), new WorldLocation(player.getLocation()));
			source.sendMessage(Text.of(TextColors.AQUA, "You set your home to your location."));
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
	
}
