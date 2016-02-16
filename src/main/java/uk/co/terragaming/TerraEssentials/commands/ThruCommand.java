package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class ThruCommand {
	
	@Command("thru")
	@Desc("Jump through the wall in front of you.")
	@Perm("tc.core.thru")
	@Alias("through")
	public CommandResult onThru(Context context) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			Player player = (Player) source;
			
			BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(25).build();
			BlockRayHit<World> finalHitRay = null;
			Location<World> location = null;
			
			while (playerBlockRay.hasNext())
			{
				BlockRayHit<World> currentHitRay = playerBlockRay.next();

				if(finalHitRay != null && player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
				{
					location = currentHitRay.getLocation();
					break;
				}
				else if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
				{
					continue;
				}
				else
				{
					finalHitRay = currentHitRay;
				}
			}

			if (finalHitRay != null && location != null)
			{
				if (player.setLocationSafely(location))
				{
					player.sendMessage(Text.of(TextColors.AQUA, "You have jumped through the wall."));
				}
				else
				{
					player.sendMessage(Text.of(TextColors.RED, "No free spot ahead of you found."));
				}
			}
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
}
