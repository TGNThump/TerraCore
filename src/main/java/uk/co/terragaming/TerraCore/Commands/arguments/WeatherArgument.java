package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;

import com.google.common.collect.Lists;


public class WeatherArgument implements ArgumentParser{

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == Weather.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		checkTypeSupported(type);
		
		if (arg.equalsIgnoreCase("sun") || arg.equalsIgnoreCase("clear")) return (T) Weathers.CLEAR;
		if (arg.equalsIgnoreCase("rain")) return (T) Weathers.RAIN;
		if (arg.equalsIgnoreCase("storm") || arg.equalsIgnoreCase("thunder") || arg.equalsIgnoreCase("thunderstorm")) return (T) Weathers.THUNDER_STORM;
		
		throw getArgumentException(type, arg);
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		if (prefix.isEmpty()) return Lists.newArrayList("sun", "rain", "storm");
		else return Lists.newArrayList("sun", "rain", "storm", "clear", "thunder", "thunderstorm");
	}
}
