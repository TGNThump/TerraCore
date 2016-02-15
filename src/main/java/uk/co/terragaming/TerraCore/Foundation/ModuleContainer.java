package uk.co.terragaming.TerraCore.Foundation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;

import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Util.Text.MyText;

import com.google.common.collect.Lists;

public class ModuleContainer {
	
	private final Class<?> mClass;
	private Object module;
	
	private String name;
	private boolean enabled;
	private ModuleContainer parent;
	private List<ModuleContainer> children = Lists.newArrayList();
	
	public ModuleContainer(Class<?> module){
		this.mClass = module;
	}
	
	public void construct(){
		if (!isEnabled()) return;
		try {
			module = mClass.newInstance();
			TerraPlugin.instance.logger.info(MyText.repeat("   ", getDepth()) + "<h>"  + getName() + "<r> Initialized.");
			Sponge.getEventManager().registerListeners(TerraPlugin.instance, module);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			enabled = false;
		}
		
		children.forEach((child) -> {
			child.construct();
		});
	}
	
	public int getDepth(){
		if (parent == null) return 0;
		return (parent.getDepth() + 1);
	}
	
	protected void registerChild(ModuleContainer container) {
		checkNotNull(container);
		children.add(container);
	}

	public Object get(){
		return module;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public boolean isConstructed(){
		return module != null;
	}
	
	public String getName(){
		return name;
	}
	
	public ModuleContainer getParent(){
		return parent;
	}
	
	public Collection<ModuleContainer> getChildren(){
		return children;
	}
	
	public ModuleContainer name(String name){
		this.name = name;
		return this;
	}
	
	public ModuleContainer enabled(boolean enabled){
		this.enabled = enabled;
		return parent;
	}
	
	public ModuleContainer parent(ModuleContainer parent){
		this.parent = parent;
		parent.registerChild(this);
		return this;
	}
}
