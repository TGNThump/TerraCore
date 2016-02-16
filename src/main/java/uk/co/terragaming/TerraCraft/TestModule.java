package uk.co.terragaming.TerraCraft;

import java.util.Optional;

import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Commands.Flag;
import uk.co.terragaming.TerraCore.Commands.MethodCommandService;
import uk.co.terragaming.TerraCore.Commands.annotations.Alias;
import uk.co.terragaming.TerraCore.Commands.annotations.Command;
import uk.co.terragaming.TerraCore.Commands.annotations.Desc;
import uk.co.terragaming.TerraCore.Commands.annotations.Perm;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraCore.Util.Context;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.Inject;

@Module(name="TestModule")
public class TestModule extends GuiceModule{
	
	@Inject
	TerraLogger logger;
	
	@Inject
	CommandManager cmdService;
	
	@Inject
	TerraPlugin plugin;
	
	@Inject
	MethodCommandService commandService;
	
	@Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("<l>GameStartedServerEvent<r>");
        commandService.registerCommands(plugin, this);
    }	
	
	@Command("character")
	@Desc("All Character Commands.")
	@Perm("character")
	@Alias("c")
	public CommandResult onCommand(Context context){
		context.get(CommandSource.class).sendMessage(Text.of("character - done"));
		return CommandResult.success();
	}
	
	@Command("character create")
	@Desc("Create a new Character")
	@Perm("character.create")
	@Alias("c") @Alias("new")
	public CommandResult onCommand2( Context context,
		@Desc("Force the character creation.") @Perm("character.create.force") @Alias("f") Flag<Boolean> force,
		@Desc("The new characters name.") String name,
		@Desc("The new characters age.") Optional<Integer> age
	){
		context.get(CommandSource.class).sendMessage(Text.of("character create - done"));
		return CommandResult.success();
	}
}