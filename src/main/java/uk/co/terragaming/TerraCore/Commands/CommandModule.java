package uk.co.terragaming.TerraCore.Commands;

import org.spongepowered.api.Sponge;

import uk.co.terragaming.TerraCore.CoreModule;
import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;

import com.google.inject.Inject;
import com.google.inject.Provides;

@Module(name="CommandModule", parent = CoreModule.class)
public class CommandModule extends GuiceModule{
	
	@Inject
	TerraPlugin plugin;
	
	private CommandHandler commandHandler;
	
	@Override
	public void onEnable() {
		commandHandler = new CommandHandler(plugin);
		Sponge.getServiceManager().setProvider(plugin, MethodCommandService.class, commandHandler);
		Sponge.getEventManager().registerListeners(plugin, commandHandler);
	}
	
	@Provides
	MethodCommandService getCommandHandler(){
		return commandHandler;
	}
	
}