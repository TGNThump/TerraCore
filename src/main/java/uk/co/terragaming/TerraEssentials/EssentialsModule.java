package uk.co.terragaming.TerraEssentials;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;

import com.google.inject.Inject;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Commands.MethodCommandService;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraEssentials.commands.GamemodeCommand;

@Module(name="EssentialsModule")
public class EssentialsModule extends GuiceModule{

	@Inject
	TerraPlugin plugin;
	
	@Inject
	MethodCommandService commandService;
	
	@Listener
    public void onInitialize(GameInitializationEvent event) {
		commandService.registerCommands(plugin, new GamemodeCommand());
	}
	
}