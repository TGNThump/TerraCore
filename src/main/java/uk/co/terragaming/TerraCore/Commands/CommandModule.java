package uk.co.terragaming.TerraCore.Commands;

import org.spongepowered.api.Sponge;

import uk.co.terragaming.TerraCore.CoreModule;
import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Commands.arguments.BooleanArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.CatalogArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.CharArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.EnchantmentArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.EnumArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.GamemodeArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.NumberArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.PlayerArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.StringArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.UserArgument;
import uk.co.terragaming.TerraCore.Commands.arguments.WeatherArgument;
import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;

import com.google.inject.Inject;
import com.google.inject.Provides;

@Module(name="CommandModule", parent = CoreModule.class)
public class CommandModule extends GuiceModule{
	
	@Inject
	TerraPlugin plugin;
	
	private CommandHandler commandHandler;
	
	@Provides
	MethodCommandService getCommandHandler(TerraPlugin plugin){
		if (commandHandler == null){
			
			commandHandler = inject(new CommandHandler(plugin));
			Sponge.getServiceManager().setProvider(plugin, MethodCommandService.class, commandHandler);
			Sponge.getEventManager().registerListeners(plugin, commandHandler);
			
			commandHandler.addArgumentParser(plugin, new EnumArgument());
			commandHandler.addArgumentParser(plugin, new CatalogArgument());
			commandHandler.addArgumentParser(plugin, new BooleanArgument());
			commandHandler.addArgumentParser(plugin, new CharArgument());
			commandHandler.addArgumentParser(plugin, new NumberArgument());
			commandHandler.addArgumentParser(plugin, new StringArgument());
			commandHandler.addArgumentParser(plugin, new UserArgument());
			commandHandler.addArgumentParser(plugin, new PlayerArgument());
			commandHandler.addArgumentParser(plugin, new GamemodeArgument());
			commandHandler.addArgumentParser(plugin, new EnchantmentArgument());
			commandHandler.addArgumentParser(plugin, new WeatherArgument());
		}
		return commandHandler;
	}
	
}