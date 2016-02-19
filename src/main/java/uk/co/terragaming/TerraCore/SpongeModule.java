package uk.co.terragaming.TerraCore;

import org.spongepowered.api.Game;
import org.spongepowered.api.GameDictionary;
import org.spongepowered.api.GameState;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.property.PropertyRegistry;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.service.pagination.PaginationService;

import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;

import com.google.inject.Provides;

@Module(name = "SpongeModule", parent = TerraCore.class)
public class SpongeModule extends GuiceModule{
	
	@Override 
	protected void configure() {
		bind(CommandManager.class).toInstance(Sponge.getCommandManager());
		bind(Scheduler.class).toInstance(Sponge.getScheduler());
		bind(GameDictionary.class).toInstance(Sponge.getDictionary());
		bind(PropertyRegistry.class).toInstance(Sponge.getGame().getPropertyRegistry());
	}
	
	@Provides
	GameState getState(Game game){
		return game.getState();
	}
	
	@Provides
	Server getServer(){
		return Sponge.getGame().getServer();
	}
	
	@Provides
	PaginationService getPaginationService(){
		return Sponge.getServiceManager().provideUnchecked(PaginationService.class);
	}
}
