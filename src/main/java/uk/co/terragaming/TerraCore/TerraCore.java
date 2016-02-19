package uk.co.terragaming.TerraCore;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;

import uk.co.terragaming.TerraCore.Commands.MethodCommandService;
import uk.co.terragaming.TerraCore.Config.Config;
import uk.co.terragaming.TerraCore.Config.MainConfig;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Names;

@Module(name = PomData.NAME, version = PomData.VERSION)
public class TerraCore extends GuiceModule{
	
	@Inject
	CorePlugin plugin;
	
	@Inject
	MethodCommandService commandService;
	
	@Listener
    public void onInitialize(GameInitializationEvent event) {
		commandService.registerCommands(plugin, new CoreCommands());
	}
		
	@Provides
	TerraLogger getTerraLogger(CorePlugin plugin){
		return plugin.logger;
	}
	
	@Provides
	ModuleManager provideModuleManager(){
		return plugin.moduleManager;
	}
	
	@Provides
	MainConfig provideConfig(CorePlugin plugin){
		return plugin.config;
	}
	
	@Override
	public void configure(){
		bind(Config.class).annotatedWith(Names.named("MainConfig")).to(MainConfig.class);
	}
	
}
