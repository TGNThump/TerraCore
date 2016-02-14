package uk.co.terragaming.TerraCore.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

public class Context {
	
	private final Map<Object, Object> locals = new HashMap<Object, Object>();
	
	public boolean containsKey(Object key){
		return locals.containsKey(key);
	}
	
	public boolean containsValue(Object value){
		return locals.containsValue(value);
	}
	
	@Nullable
	public Object get(Object key){
		return locals.get(key);
	}
	
	public Optional<Object> getOpt(Object key){
		return Optional.ofNullable(get(key));
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> key){
		return (T) locals.get(key);
	}
	
	@Nullable
	public <T> Optional<T> getOpt(Class<T> key){
		return Optional.ofNullable(get(key));
	}
	
	@Nullable
	public Object put(Object key, Object value){
		return locals.put(key, value);
	}
	
}
