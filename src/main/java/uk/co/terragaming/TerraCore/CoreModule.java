package uk.co.terragaming.TerraCore;

import java.io.File;

import javax.inject.Singleton;

import com.google.inject.name.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {
	
	@Provides
	@Singleton
	TerraPlugin getPlugin(){
		return TerraPlugin.instance;
	}
	
	@Provides
	@Named("configDir")
	@Singleton
	File getConfigDir(TerraPlugin plugin){
		return plugin.configDir;
	}
	
}
