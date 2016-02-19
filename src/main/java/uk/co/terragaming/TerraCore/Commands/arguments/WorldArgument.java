package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


public class WorldArgument implements ArgumentParser{

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == World.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		Optional<World> world = Sponge.getGame().getServer().getWorld(arg);
		if (!world.isPresent()) throw getArgumentException(type, arg);
		return (T) world.get();
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		List<String> suggestions = Lists.newArrayList();
		for (World w : Sponge.getGame().getServer().getWorlds()){
			suggestions.add(w.getName());
		}
		
		return suggestions;
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Loaded World";
	}
	
}
