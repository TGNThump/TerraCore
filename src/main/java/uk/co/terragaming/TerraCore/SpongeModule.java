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
import org.spongepowered.api.data.ImmutableDataRegistry;
import org.spongepowered.api.data.manipulator.DataManipulatorRegistry;
import org.spongepowered.api.data.property.PropertyRegistry;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.service.persistence.SerializationManager;
import org.spongepowered.api.service.scheduler.SchedulerService;

import dagger.Module;
import dagger.Provides;

@Module
public class SpongeModule {
	
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
	SchedulerService getScheduler(){
		return Sponge.getScheduler();
	}
	
	@Provides @Singleton
	SerializationManager getSerializationService(){
		return Sponge.getSerializationService();
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
	CommandService getCommandDispatcher(){
		return Sponge.getCommandDispatcher();
	}
	
	@Provides @Singleton
	ChannelRegistrar getChannelRegistrar(){
		return Sponge.getChannelRegistrar();
	}
	
	@Provides @Singleton
	DataManipulatorRegistry getManipulatorRegistry(){
		return Sponge.getManipulatorRegistry();
	}
	
	@Provides @Singleton
	ImmutableDataRegistry getImmutableManipulatorRegistry(){
		return Sponge.getImmutableManipulatorRegistry();
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
