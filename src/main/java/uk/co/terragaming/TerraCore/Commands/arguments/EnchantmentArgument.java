package uk.co.terragaming.TerraCore.Commands.arguments;

import java.lang.reflect.Field;
import java.util.List;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.util.annotation.CatalogedBy;

import com.google.common.collect.Lists;


public class EnchantmentArgument extends CatalogArgument{
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == Enchantment.class;
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
							suggestions.add(((CatalogType) f.get(null)).getId());
						}
					} catch (Exception ex){}
				}
			}
		} catch (Exception ex){}
		
		return suggestions;
	}
}
