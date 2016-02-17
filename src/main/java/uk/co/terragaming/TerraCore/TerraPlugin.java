package uk.co.terragaming.TerraCore;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import uk.co.terragaming.TerraCore.Config.MainConfig;
import uk.co.terragaming.TerraCore.Enums.ServerMode;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.MyText;

import com.google.inject.Injector;
import com.google.inject.Module;

@Plugin(id = "TC", name = "TerraCore", version = "0.0.4")
public class TerraPlugin {
	
	public static TerraPlugin instance;
	public ModuleManager moduleManager;
	public ServerMode serverMode = ServerMode.STARTING;
	public Logger baseLogger;
	public TerraLogger logger;
	public Injector baseInjector;
	public Injector injector;
	
	public MainConfig config;
	
	@Inject
	private PluginManager pluginManager;
	
	@Inject
	public void setBaseInjector(Injector baseInjector){
		this.baseInjector = baseInjector;
	}
	
	@Inject
	public void setLogger(Logger logger){
		baseLogger = logger;
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
		
		PluginContainer plugin = getPluginContainer();
				
		String spacer = MyText.repeat("-", (" Launching " + plugin.getName() + " V" + plugin.getVersion() + " ").length());
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
		
		logger.info("<l>Loading Configuration.<r>");

		config = new MainConfig();
		
		logger.blank();
		logger.info("Successfully Loaded Configuration.");
		
		logger.blank();
		logger.info("<l>Creating Injector.<r>");
		
		Collection<Module> modules = moduleManager.getGuiceModules();
		
		injector = baseInjector.createChildInjector(modules);
		
//		logger.blank();
//		injector.getAllBindings().forEach((key, value) -> {
//			logger.info("<h>%s<r>: %s", key, value);
//		});
		logger.blank();
		
		logger.info("Injector Created.");
		
		moduleManager.forEach((c)->{
			Object obj = c.get();
			if (obj == null) return;
			injector.injectMembers(obj);
		});
		
		logger.info("Module Injection Complete.");
		
		moduleManager.onEnableAll();
		
		logger.blank();
		logger.info("<l>Server loaded in <h>%s<l> mode.<r>", config.server.mode.toString());
		logger.blank();
	}
	
	@Listener
	public void onServerStarted(GameStartedServerEvent event){
		serverMode = config.server.mode;
	}
	
	@Listener
	public void onServerStopping(GameStoppingServerEvent event){
		serverMode = ServerMode.STOPPING;
	}
	
	@Listener
	public void onServerStopped(GameStoppedServerEvent event){
		config.save();
	}
	
	public static PluginContainer getPluginContainer(){
		return instance.pluginManager.fromInstance(instance).get();
	}
}
