package uk.co.terragaming.TerraCore;

import java.io.File;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.service.config.ConfigDir;

public class TerraPlugin {
	
	public static TerraPlugin instance;	
	
	@Inject
	public Logger logger;

	@Inject
	@ConfigDir(sharedRoot = false)
	public File configDir;
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){
		instance = this;
		System.setProperty("jansi.passthrough", "true");
	}
	
}
