package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.property.block.PassableProperty;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class JumpCommand {
	
	@Command("jump")
	@Desc("Jump to where you are looking.")
	@Perm("tc.core.jump")
	public CommandResult onJump(Context context) throws CommandException{
		CommandSource source = context.get(CommandSource.class);
		
		if (source instanceof Player){
			Player player = (Player) source;
			
			BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(350).build();
			BlockRayHit<World> finalHitRay = null;
			
			while (playerBlockRay.hasNext()) {
				BlockRayHit<World> currentHitRay = playerBlockRay.next();

				//If the block it hit was air or a passable block such as tall grass, keep going.
				if (!player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR) &&
					!currentHitRay.getLocation().getProperty(PassableProperty.class).get().getValue()){
					
					finalHitRay = currentHitRay;
					break;
				}
			}

			if (finalHitRay == null) {
				player.sendMessage(Text.of(TextColors.RED, "Could not find the block you're looking at within range."));
			} else {
				// Add 1 so that players do not spawn in the block
				if (player.setLocationSafely(finalHitRay.getLocation().add(0, 1, 0))) {
					player.sendMessage(Text.of(TextColors.AQUA, "You have jumped to the block you were looking at."));
				} else {
					player.sendMessage(Text.of(TextColors.RED, "Couldn't safely jump you to where your looking at."));
				}
			}
			
			return CommandResult.success();
		} else {
			source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
			return CommandResult.empty();
		}
	}
}
