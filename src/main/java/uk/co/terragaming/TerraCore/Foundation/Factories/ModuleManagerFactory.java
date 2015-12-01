package uk.co.terragaming.TerraCore.Foundation.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.CoreModule;
import uk.co.terragaming.TerraCore.Foundation.ModuleManager;
import dagger.Component;

@Singleton
@Component(modules = CoreModule.class)
public interface ModuleManagerFactory extends BaseFactory<ModuleManager>{
	ModuleManager make();
}
