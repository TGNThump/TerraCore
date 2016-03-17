package uk.co.terragaming.TerraCore;

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

@Plugin(id = "TC", name = PomData.NAME, version = PomData.VERSION)
public class CorePlugin {
	
	public static String ID;
	public static String NAME;
	public static String VERSION;
	
	private static CorePlugin instance;
	
	public static boolean isDevelopmentMode(){
		return instance.config.devMode;
	}
	
	public ModuleManager moduleManager;
	public TerraLogger logger;
	public Injector injector;
	public MainConfig config;
	
	private Injector baseInjector;
	
	private PluginContainer pluginContainer;
	
	public static CorePlugin instance(){
		return instance;
	}
	
	public CorePlugin(){
		instance = this;
		System.setProperty("jansi.passthrough", "true");
		moduleManager = new ModuleManager();
		moduleManager.registerAll();
	}
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){
		config = new MainConfig();
		
		if (isDevelopmentMode()){
			String msg = "<l> Launching " + NAME + " v" + VERSION + " ";
			String spacer = MyText.repeat("-", (msg).length());
			
			logger.blank();
			logger.info(spacer);
			logger.info(msg);
			logger.info(spacer);
			logger.blank();
		}
		
		moduleManager.constructAll();
		injector = moduleManager.createInjector(baseInjector);
		moduleManager.onEnableAll();		
		
		if (isDevelopmentMode()){
			logger.blank();
			logger.info("All <h>%s<r> enabled mechanics have been loaded.", moduleManager.getEnabledCount());
			logger.blank();
		} else {
			logger.info("Launched " + NAME + " v" + VERSION + " with " + moduleManager.getEnabledCount() + " modules.");
		}	
	}
	
	@Listener
	public void onServerStopped(GameStoppedServerEvent event){
		config.save();
	}
	
	@Inject
	public void setLogger(Logger logger){
		logger = new TerraLogger(logger);
	}
	
	@Inject
	public void setBaseInjector(Injector baseInjector){
		this.baseInjector = baseInjector;
	}
	
	@Inject
	public void setPluginManager(PluginManager pluginManager){
		pluginContainer = pluginManager.fromInstance(instance).get();
		
		ID = pluginContainer.getId();
		NAME = pluginContainer.getName();
		VERSION = pluginContainer.getVersion();
	}
	
	public Injector getInjector(){
		return injector;
	}
	
	public Injector getBaseInjector(){
		return baseInjector;
	}
	
	public TerraLogger getLogger(){
		return logger;
	}
	
	public PluginContainer getPluginContainer(){
		return pluginContainer;
	}
}
