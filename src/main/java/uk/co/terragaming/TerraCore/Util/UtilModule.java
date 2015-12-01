package uk.co.terragaming.TerraCore.Util;

import javax.inject.Singleton;

import org.slf4j.Logger;

import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.Provides;

import dagger.Module;

@Module
public class UtilModule {
	
	@Provides @Singleton
	Logger getLogger(){
		return new TerraLogger();
	}
	
}
