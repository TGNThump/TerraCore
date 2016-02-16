package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


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
		if (!player.isPresent()) throw getArgumentException(type, arg);
		return (T) player.get();
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		List<String> suggestions = Lists.newArrayList();
		for (Player p : Sponge.getGame().getServer().getOnlinePlayers()){
			suggestions.add(p.getName());
		}
		
		return suggestions;
	}
	
	@Override
	public Text getSuggestionText(Class<?> type, String prefix) throws IllegalArgumentException {
		return Text.EMPTY;
	}
	
	@Override
	public String getArgumentTypeName(Class<?> type) throws IllegalArgumentException {
		checkTypeSupported(type);
		return "Online Player";
	}
	
}
