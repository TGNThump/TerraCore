package uk.co.terragaming.TerraEssentials;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Commands.MethodCommandService;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraEssentials.commands.ClearCommand;
import uk.co.terragaming.TerraEssentials.commands.EnchantCommand;
import uk.co.terragaming.TerraEssentials.commands.FeedCommand;
import uk.co.terragaming.TerraEssentials.commands.FlyCommand;
import uk.co.terragaming.TerraEssentials.commands.GamemodeCommand;
import uk.co.terragaming.TerraEssentials.commands.HealCommand;
import uk.co.terragaming.TerraEssentials.commands.HomeCommand;
import uk.co.terragaming.TerraEssentials.commands.InvseeCommand;
import uk.co.terragaming.TerraEssentials.commands.JumpCommand;
import uk.co.terragaming.TerraEssentials.commands.SpawnCommand;
import uk.co.terragaming.TerraEssentials.commands.SpeedCommand;
import uk.co.terragaming.TerraEssentials.commands.ThruCommand;
import uk.co.terragaming.TerraEssentials.commands.TimeCommand;
import uk.co.terragaming.TerraEssentials.commands.VanishCommand;
import uk.co.terragaming.TerraEssentials.commands.WeatherCommand;
import uk.co.terragaming.TerraEssentials.config.EssentialsData;
import uk.co.terragaming.TerraEssentials.listeners.PlayerJoinListener;

import com.google.inject.Inject;
import com.google.inject.Provides;

@Module(name="EssentialsModule")
public class EssentialsModule extends GuiceModule{

	@Inject
	TerraPlugin plugin;
	
	@Inject
	MethodCommandService commandService;
	
	EssentialsData data;
	
	@Listener
    public void onInitialize(GameInitializationEvent event) {
		commandService.registerCommands(plugin, new GamemodeCommand());
		commandService.registerCommands(plugin, new FlyCommand());
		commandService.registerCommands(plugin, new SpeedCommand());
		commandService.registerCommands(plugin, new ClearCommand());
		commandService.registerCommands(plugin, new EnchantCommand());
		commandService.registerCommands(plugin, new HealCommand());
		commandService.registerCommands(plugin, new FeedCommand());
		commandService.registerCommands(plugin, new InvseeCommand());
		commandService.registerCommands(plugin, new ThruCommand());
		commandService.registerCommands(plugin, new JumpCommand());
		commandService.registerCommands(plugin, new VanishCommand());
		commandService.registerCommands(plugin, new WeatherCommand());
		commandService.registerCommands(plugin, new SpawnCommand());
		commandService.registerCommands(plugin, new TimeCommand());
		commandService.registerCommands(plugin, new HomeCommand());
	}
	
	@Listener
	public void onServerStarting(GameStartingServerEvent event){
		data = new EssentialsData();
		Sponge.getEventManager().registerListeners(plugin, inject(new PlayerJoinListener()));
	}
	
	@Listener
	public void onServerStopping(GameStoppedServerEvent event){
		data.save();
	}
	
	@Provides
	EssentialsData getData(){
		return data;
	}
	
}