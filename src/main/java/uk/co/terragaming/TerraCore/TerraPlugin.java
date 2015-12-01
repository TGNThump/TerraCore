package uk.co.terragaming.TerraCore;

import java.io.File;

import javax.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.DefaultConfig;

import uk.co.terragaming.TerraCore.Enums.ServerMode;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Foundation.Factories.DaggerModuleManagerFactory;
import uk.co.terragaming.TerraCore.Util.Factories.DaggerTerraLoggerFactory;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.Text;

public class TerraPlugin {
	
	public static TerraPlugin instance;
	protected ModuleManager moduleManager;
	protected ConfigurationLoader<CommentedConfigurationNode> configManager;
	public ServerMode serverMode = ServerMode.STARTING;
	public Logger baseLogger;
	public TerraLogger logger;
	public File configDir;
	public File config;
	
	@Inject
	public void setLogger(Logger logger){
		baseLogger = logger;
	}
	
	@Inject
	public void setConfigDir(@ConfigDir(sharedRoot = false) File file){
		configDir = file;
	}
	
	@Inject
	public void setConfig(@DefaultConfig(sharedRoot = false) File file){
		config = file;
	}
	
	@Inject
	public void setConfigManager(@DefaultConfig(sharedRoot = true) ConfigurationLoader<CommentedConfigurationNode> manager){
		configManager = manager;
	}
	
	public TerraPlugin(){
		instance = this;
		System.setProperty("jansi.passthrough", "true");
		moduleManager = DaggerModuleManagerFactory.create().make();
		moduleManager.registerAll();
	}
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){
		logger = DaggerTerraLoggerFactory.create().make();
		
		PluginContainer plugin = event.getGame().getPluginManager().fromInstance(this).get();
				
		String spacer = Text.repeat("-", (" Launching " + plugin.getName() + " V" + plugin.getVersion() + " ").length());
		String msg = " <l> Launching " + plugin.getName() + " V" + plugin.getVersion() + " ";
		
		logger.blank();
		logger.info(spacer);
		logger.info(msg);
		logger.info(spacer);
		logger.blank();
		
		moduleManager.constructAll();
		
		logger.blank();
		logger.info("<l>All <h>%s<l> enabled mechanics have been loaded.<r>", moduleManager.getModules().size());
		logger.blank();
		
		//serverMode = ServerMode.valueOf(getConfig().get("TerraCraft.Server.Mode").toString());
	}
}
