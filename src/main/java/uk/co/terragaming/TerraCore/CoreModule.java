package uk.co.terragaming.TerraCore;

import javax.inject.Singleton;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;

import uk.co.terragaming.TerraCore.Commands.MethodCommandService;
import uk.co.terragaming.TerraCore.Enums.ServerMode;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.Inject;
import com.google.inject.Provides;

@Module(name = "CoreModule")
public class CoreModule extends GuiceModule{
	
	@Inject
	TerraPlugin plugin;
	
	@Inject
	MethodCommandService commandService;
	
	@Listener
    public void onInitialize(GameInitializationEvent event) {
		commandService.registerCommands(plugin, new CoreCommands());
	}
		
	@Provides
	TerraLogger getTerraLogger(TerraPlugin plugin){
		return plugin.logger;
	}
	
	@Provides
	ServerMode provideServerMode(TerraPlugin plugin){
		return plugin.serverMode;
	}
	
	@Provides @Singleton
	ModuleManager provideModuleManager(){
		return new ModuleManager();
	}
	
}
