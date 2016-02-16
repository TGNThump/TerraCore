package uk.co.terragaming.TerraCore.Commands.arguments;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.util.annotation.CatalogedBy;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


public class GamemodeArgument extends CatalogArgument{

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == GameMode.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		Collection<GameMode> catalog = Sponge.getRegistry().getAllOf(GameMode.class);
		for (CatalogType ct : catalog){
			if (ct.getId().equalsIgnoreCase(arg) || ct.getName().replace(' ', '_').equalsIgnoreCase(arg)){
				return (T) ct;
			}
		}
		
		if (arg.equals("0") || arg.equals("s")) return (T) GameModes.SURVIVAL;
		if (arg.equals("1") || arg.equals("c")) return (T) GameModes.CREATIVE;
		if (arg.equals("2") || arg.equals("a")) return (T) GameModes.ADVENTURE;
		if (arg.equals("3")) return (T) GameModes.SPECTATOR;
		
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
