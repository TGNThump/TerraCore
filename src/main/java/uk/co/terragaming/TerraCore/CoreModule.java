package uk.co.terragaming.TerraCore;

import java.io.File;

import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.name.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = SpongeModule.class)
public class CoreModule {
	
	@Provides @Singleton
	TerraPlugin getPlugin(){
		return TerraPlugin.instance;
	}
	
	@Provides @Named("configDir") @Singleton
	File getConfigDir(TerraPlugin plugin){
		return plugin.configDir;
	}
	
	@Provides @Named("config") @Singleton
	File getConfig(TerraPlugin plugin){
		return plugin.config;
	}
	
	@Provides @Singleton
	TerraLogger getLogger(){
		return new TerraLogger();
	}
	
	@Provides @Singleton
	ModuleManager provideModuleManager(){
		return new ModuleManager();
	}
	
}
