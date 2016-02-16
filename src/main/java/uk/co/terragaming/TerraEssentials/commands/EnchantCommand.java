package uk.co.terragaming.TerraEssentials.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.Flag;
import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Commands.exceptions.CommandException;
import uk.co.terragaming.TerraCore.Util.Context;


public class EnchantCommand {
	
	@Command("enchant")
	@Desc("Enchants the item in the players hand.")
	@Perm("tc.core.enchant")
	@Alias("ench")
	public CommandResult onEnchant(Context context,
			@Desc("The enchantment to apply.") Enchantment enchantment,
			@Desc("The level enchantment to apply.") Integer level,
			@Desc("Force the enchant if unsafe.") @Perm("tc.core.enchant.unsafe") @Alias("-f") Flag<Boolean> force
		) throws CommandException{
			CommandSource source = context.get(CommandSource.class);
			
			if (source instanceof Player){
				Player player = (Player) source;
				
				if (player.getItemInHand().isPresent()){
					
					ItemStack itemInHand = player.getItemInHand().get();
					
					if (!enchantment.canBeAppliedByTable(itemInHand)){
						source.sendMessage(Text.of(TextColors.RED, "This enchantment cannot be applied to this item."));
						return CommandResult.empty();
					}
					
					if (!force.isPresent()){
						if (enchantment.getMaximumLevel() < level){
							source.sendMessage(Text.of(TextColors.RED, "Enchantment level above safe level."));
							if (source.hasPermission("tc.core.enchant.unsafe"))
								source.sendMessage(Text.of(TextColors.RED, "Use the ", TextColors.YELLOW, "-force", TextColors.RED, " flag to continue anyway."));
							return CommandResult.empty();
						}
					}
					
					EnchantmentData enchantData = itemInHand.getOrCreate(EnchantmentData.class).get();
					ItemEnchantment itemEnchant = new ItemEnchantment(enchantment, level);
					ItemEnchantment sameEnchant = null;
					
					for (ItemEnchantment ench : enchantData.enchantments()){
						if (ench.getEnchantment().getId().equals(enchantment.getId())){
							sameEnchant = ench;
							break;
						}
					}
					
					if (sameEnchant == null){
						enchantData.set(enchantData.enchantments().add(itemEnchant));
					} else {
						enchantData.set(enchantData.enchantments().remove(sameEnchant));
						enchantData.set(enchantData.enchantments().add(itemEnchant));
					}
					
					itemInHand.offer(enchantData);
					player.setItemInHand(itemInHand);
					player.sendMessage(Text.of(TextColors.AQUA, "Enchanted item(s) in your hand."));
					
				} else {
					source.sendMessage(Text.of(TextColors.RED, "You must be holding an item to enchant."));
					return CommandResult.empty();
				}
				
			} else {
				source.sendMessage(Text.of(TextColors.RED, "This command can only be run as a Player."));
				return CommandResult.empty();
			}
			
			
		return CommandResult.success();
	}
}
