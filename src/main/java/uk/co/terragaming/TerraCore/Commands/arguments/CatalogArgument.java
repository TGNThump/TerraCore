package uk.co.terragaming.TerraCore.Commands.arguments;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.annotation.CatalogedBy;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;

/**
 * A {@link CatalogType} {@link ArgumentParser}
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public class CatalogArgument implements ArgumentParser {
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return CatalogType.class.isAssignableFrom(type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		Collection<? extends CatalogType> catalog = Sponge.getRegistry().getAllOf((Class<? extends CatalogType>) type);
		for (CatalogType ct : catalog){
			if (ct.getId().equalsIgnoreCase(arg) || ct.getName().replace(' ', '_').equalsIgnoreCase(arg)){
				return (T) ct;
			}
		}
		
		throw getArgumentException(type, arg);
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		List<String> suggestions = Lists.newArrayList();
		try{
			CatalogedBy cBy = type.getAnnotation(CatalogedBy.class);
			if (cBy == null) return suggestions;
			
			for (Class<?> catalog : cBy.value()){
				for (Field f : catalog.getFields()){
					try{
						if (type.isAssignableFrom(f.getType())){
							suggestions.add(((CatalogType) f.get(null)).getName().replace(' ', '_'));
						}
					} catch (Exception ex){}
				}
			}
		} catch (Exception ex){}
		
		return suggestions;
	}
}
