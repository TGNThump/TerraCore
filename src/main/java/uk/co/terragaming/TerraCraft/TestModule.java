package uk.co.terragaming.TerraCraft;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

import uk.co.terragaming.TerraCore.Foundation.GuiceModule;
import uk.co.terragaming.TerraCore.Foundation.Module;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;

import com.google.inject.Inject;

@Module(name="TestModule")
public class TestModule extends GuiceModule{
	
	@Inject
	TerraLogger logger;
	
	@Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("<l>GameStartedServerEvent<r>");
    }
	
}