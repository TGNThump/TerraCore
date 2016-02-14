package uk.co.terragaming.TerraCore;

import org.spongepowered.api.Game;
import org.spongepowered.api.GameDictionary;
import org.spongepowered.api.GameState;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.property.PropertyRegistry;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.scheduler.Scheduler;

import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;

import com.google.inject.Provides;

@Module(name = "SpongeModule", parent = CoreModule.class)
public class SpongeModule extends GuiceModule{
	
	@Override 
	protected void configure() {
		bind(Server.class).toInstance(Sponge.getGame().getServer());
		bind(CommandManager.class).toInstance(Sponge.getCommandDispatcher());
		bind(Scheduler.class).toInstance(Sponge.getScheduler());
		bind(GameDictionary.class).toInstance(Sponge.getDictionary());
		bind(ChannelRegistrar.class).toInstance(Sponge.getChannelRegistrar());
		bind(PropertyRegistry.class).toInstance(Sponge.getGame().getPropertyRegistry());
	}
	
	@Provides
	GameState getState(Game game){
		return game.getState();
	}
}
