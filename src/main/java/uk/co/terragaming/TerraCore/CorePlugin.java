package uk.co.terragaming.TerraCore;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import uk.co.terragaming.TerraCore.Config.MainConfig;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import uk.co.terragaming.TerraCore.Util.Text.MyText;

import com.google.inject.Injector;
import com.google.inject.Module;

@Plugin(id = "TC", name = PomData.NAME, version = PomData.VERSION)
public class CorePlugin {
	
	public static CorePlugin instance;
	public ModuleManager moduleManager;
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
	
	public CorePlugin(){
		instance = this;
		System.setProperty("jansi.passthrough", "true");
		moduleManager = new ModuleManager();
		moduleManager.registerAll();
	}
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){		
		logger = new TerraLogger();
		config = new MainConfig();
		
		PluginContainer plugin = getPluginContainer();
		
		if (isDevelopmentMode()){
			String spacer = MyText.repeat("-", (" Launching <h>" + plugin.getName() + " v" + plugin.getVersion() + " ").length());
			String msg = "<l> Launching " + plugin.getName() + " v" + plugin.getVersion() + " ";
			
			logger.blank();
			logger.info(spacer);
			logger.info(msg);
			logger.info(spacer);
			logger.blank();
		}
		
		moduleManager.constructAll();
		
		Collection<Module> modules = moduleManager.getGuiceModules();
		
		injector = baseInjector.createChildInjector(modules);
				
		moduleManager.forEach((c)->{
			Object obj = c.get();
			if (obj == null) return;
			injector.injectMembers(obj);
		});
				
		moduleManager.onEnableAll();
		
		if (isDevelopmentMode()){
			logger.blank();
			logger.info("All <h>%s<r> enabled mechanics have been loaded.", moduleManager.getEnabledCount());
			logger.blank();
		} else {
			logger.info("Launched " + plugin.getName() + " v" + plugin.getVersion() + " with " + moduleManager.getEnabledCount() + " modules.");
		}
		
	}
	
	@Listener
	public void onServerStopped(GameStoppedServerEvent event){
		config.save();
	}
	
	public static PluginContainer getPluginContainer(){
		return instance.pluginManager.fromInstance(instance).get();
	}
	
	public static boolean isDevelopmentMode(){
		return instance.config.devMode;
	}
}
