package uk.co.terragaming.TerraCore;

import javax.inject.Singleton;

import org.spongepowered.api.Game;
import org.spongepowered.api.GameDictionary;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.GameState;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.property.PropertyRegistry;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.service.ServiceManager;

import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;

import com.google.inject.Provides;

@Module(name = "SpongeModule", parent = CoreModule.class)
public class SpongeModule extends GuiceModule{
	
	@Provides @Singleton
	Game getGame(){
		return Sponge.getGame();
	}
	
	@Provides @Singleton
	Server getServer(Game game){
		return game.getServer();
	}
	
	@Provides @Singleton
	GameRegistry getRegistry(){
		return Sponge.getRegistry();
	}
	
	@Provides @Singleton
	ServiceManager getServiceManager(){
		return Sponge.getServiceManager();
	}
	
	@Provides @Singleton
	EventManager getEventManager(){
		return Sponge.getEventManager();
	}
	
	@Provides @Singleton
	Scheduler getScheduler(){
		return Sponge.getScheduler();
	}
	
	@Provides @Singleton
	PluginManager getPluginManager(){
		return Sponge.getPluginManager();
	}
	
	@Provides @Singleton
	Platform getPlatform(){
		return Sponge.getPlatform();
	}
	
	@Provides @Singleton
	GameDictionary getDictionary(){
		return Sponge.getDictionary();
	}
	
	@Provides @Singleton
	CommandManager getCommandDispatcher(){
		return Sponge.getCommandDispatcher();
	}
	
	@Provides @Singleton
	ChannelRegistrar getChannelRegistrar(){
		return Sponge.getChannelRegistrar();
	}
	
	@Provides @Singleton
	PropertyRegistry getPropertyRegistry(Game game){
		return game.getPropertyRegistry();
	}
	
	@Provides
	GameState getState(Game game){
		return game.getState();
	}
	
	@Provides
	MinecraftVersion getMinecraftVersion(Platform platform){
		return platform.getMinecraftVersion();
	}	
}
