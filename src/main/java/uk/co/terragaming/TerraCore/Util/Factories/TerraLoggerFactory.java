package uk.co.terragaming.TerraCore.Util.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.CoreModule;
import uk.co.terragaming.TerraCore.Foundation.Factories.BaseFactory;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import dagger.Component;

@Singleton
@Component(modules = CoreModule.class)
public interface TerraLoggerFactory extends BaseFactory<TerraLogger>{
	TerraLogger make();
}
