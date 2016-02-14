package uk.co.terragaming.TerraCore.Foundation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class ModuleManager implements Iterable<ModuleContainer>{
		
	private HashMap<Class<?>, ModuleContainer> modules = new HashMap<>();
	private List<ModuleContainer> rootModules = Lists.newArrayList();
	
	public int getEnabledCount(){
		int ret = 0;
		
		for(ModuleContainer m : getModuleContainers()){
			if (m.isEnabled()) ret++;
		}
		
		return ret;
	}
	
	public Collection<ModuleContainer> getModuleContainers(){
		return modules.values();
	}
	
	public Collection<com.google.inject.Module> getGuiceModules(){
		List<com.google.inject.Module> ret = Lists.newArrayList();
		for (ModuleContainer container : getModuleContainers()){
			Object obj = container.get();
			if (obj == null) continue;
			if (!(obj instanceof com.google.inject.Module)) continue;
			ret.add((com.google.inject.Module) obj);
		}
		return ret;
	}
	
	public void constructAll(){
		rootModules.forEach((module) -> {module.construct();});
	}
	
	public void registerAll(){
		registerAll("uk.co.terragaming");
	}
	
	public void registerAll(String path){
		Reflections reflections = new Reflections(path);
		Set<Class<?>> modules = reflections.getTypesAnnotatedWith(Module.class);
		modules.forEach((module)->{
			register(module);
		});
	}
	
	public ModuleContainer register(Class<?> module){
		checkNotNull(module);
		checkArgument(module.isAnnotationPresent(Module.class));
		
		if (modules.containsKey(module)) return modules.get(module);
		
		Module annotation = module.getAnnotation(Module.class);
		return register(module, annotation);
	}

	private ModuleContainer register(Class<?> module, Module annotation) {
		checkNotNull(module);
		checkNotNull(annotation);
		
		ModuleContainer container = new ModuleContainer(module);
		
		container.name(annotation.name())
			.enabled(annotation.enabled());
		
		if (annotation.parent().equals(void.class) || annotation.parent().equals(module)){
			rootModules.add(container);
		} else {
			container.parent(register(annotation.parent()));
		}
		
		modules.put(module, container);
		return container;
	}

	@Override
	public Iterator<ModuleContainer> iterator() {
		return modules.values().iterator();
	}
	
}
