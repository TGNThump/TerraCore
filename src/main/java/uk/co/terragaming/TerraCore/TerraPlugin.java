package uk.co.terragaming.TerraCore;

import java.io.File;

import javax.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.PluginContainer;

import uk.co.terragaming.TerraCore.Enums.ServerMode;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.Text;

import com.google.inject.Injector;

public class TerraPlugin {
	
	public static TerraPlugin instance;
	protected ModuleManager moduleManager;
	protected ConfigurationLoader<CommentedConfigurationNode> configManager;
	public ServerMode serverMode = ServerMode.STARTING;
	public Logger baseLogger;
	public TerraLogger logger;
	public File configDir;
	public File config;
	public Injector baseInjector;
	public Injector injector;
	
	@Inject
	public void setBaseInjector(Injector baseInjector){
		this.baseInjector = baseInjector;
	}
	
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
		moduleManager = new ModuleManager();
		moduleManager.registerAll();
	}
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){
		logger = new TerraLogger();
		
		PluginContainer plugin = event.getGame().getPluginManager().fromInstance(this).get();
				
		String spacer = Text.repeat("-", (" Launching " + plugin.getName() + " V" + plugin.getVersion() + " ").length());
		String msg = "<l> Launching " + plugin.getName() + " V" + plugin.getVersion() + " ";
		
		logger.blank();
		logger.info(spacer);
		logger.info(msg);
		logger.info(spacer);
		logger.blank();
		
		logger.info("<l>Initializing Modules.<r>");
		logger.blank();
		
		moduleManager.constructAll();
		
		logger.blank();
		logger.info("All <h>%s<r> enabled mechanics have been loaded.", moduleManager.getEnabledCount());
		
		logger.blank();
		logger.info("<l>Creating Injector.<r>");
		
		injector = baseInjector.createChildInjector(moduleManager.getGuiceModules());

		logger.info("Injector Created.");
		
		moduleManager.forEach((c)->{
			Object obj = c.get();
			if (obj == null) return;
			injector.injectMembers(obj);
		});
		
		logger.info("Module Injection Complete.");
		
		logger.blank();
		logger.info("<l>Server loaded in <h>%s<l> mode.<r>", "MODE");
		logger.blank();
		
		//serverMode = ServerMode.valueOf(getConfig().get("TerraCraft.Server.Mode").toString());
	}
}
