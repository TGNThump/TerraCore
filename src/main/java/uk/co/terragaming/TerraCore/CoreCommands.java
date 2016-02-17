package uk.co.terragaming.TerraCore;

import javax.inject.Inject;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Config.MainConfig;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraCore.events.ConfigurationReloadEvent;

public class CoreCommands {
	
	@Inject
	MainConfig config;
	
	@Inject
	EventManager eventManager;
	
	@Inject
	ModuleManager moduleManager;
	
	@Inject
	PluginContainer pluginContainer;
	
	@Command("terracore")
	@Desc("Gets the current TerraCore version.")
	@Alias("tc")
	@Perm("tc.core")
	public CommandResult onTerraCore(Context context){
		context.get(CommandSource.class).sendMessage(Text.of(TextColors.AQUA, "Running TerraCore ", TextColors.YELLOW, "v", pluginContainer.getVersion(), TextColors.AQUA, " with ", TextColors.YELLOW, moduleManager.getEnabledCount(), TextColors.AQUA, " enabled modules."));
		return CommandResult.success();
	}
	
	
	@Command("terracore reload")
	@Desc("Reload the TerraCore configuration.")
	@Alias("r")
	@Perm("tc.core.reload")
	public CommandResult onReload(Context context){
		config.load();
		eventManager.post(new ConfigurationReloadEvent());
		context.get(CommandSource.class).sendMessage(Text.of(TextColors.AQUA, "Reloaded TerraCore Configuration."));
		return CommandResult.success();
	}
}
