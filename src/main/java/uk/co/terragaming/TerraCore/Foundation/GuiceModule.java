package uk.co.terragaming.TerraCore.Foundation;

import uk.co.terragaming.TerraCore.TerraPlugin;


public abstract class GuiceModule extends com.google.inject.AbstractModule{

	@Override
	protected void configure() {
		
	}
	
	public void onEnable() {}
	
	public <T> T inject(T object){
		TerraPlugin.instance.injector.injectMembers(object);
		return object;
	}
	
}
