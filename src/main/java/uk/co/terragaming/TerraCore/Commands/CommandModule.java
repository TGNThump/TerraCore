package uk.co.terragaming.TerraCore.Commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.ProviderExistsException;

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
	
	public CommandModule(){
		commandHandler = new CommandHandler(plugin);
		try {
			Sponge.getServiceManager().setProvider(this, MethodCommandService.class, commandHandler);
		} catch (ProviderExistsException e) {
			e.printStackTrace();
		}
		Sponge.getEventManager().registerListeners(this, commandHandler);
	}
	
	@Provides
	MethodCommandService getCommandHandler(){
		return Sponge.getServiceManager().provide(MethodCommandService.class).get();
	}
	
}