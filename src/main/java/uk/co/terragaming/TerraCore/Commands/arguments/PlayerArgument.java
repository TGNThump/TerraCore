package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;


public class PlayerArgument implements ArgumentParser {
	
	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == Player.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		Optional<Player> player = Sponge.getGame().getServer().getPlayer(arg);
		if (!player.isPresent()) throw new ArgumentException("Expected an Online Player, got '" + arg + "'");
		return (T) player.get();
	}
	
	@Override
	public List<String> suggestArguments(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		List<String> suggestions = Lists.newArrayList();
		for (Player p : Sponge.getGame().getServer().getOnlinePlayers()){
			suggestions.add(p.getName());
		}
		
		return suggestions;
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Online Player";
	}
	
}
