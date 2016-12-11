package uk.co.terragaming.TerraCore.Commands.arguments;

import java.util.List;

import com.google.common.collect.Lists;

import uk.co.terragaming.TerraCore.Commands.exceptions.ArgumentException;
import uk.co.terragaming.TerraCore.Util.WorldTime;


public class WorldTimeArgument implements ArgumentParser{

	@Override
	public boolean isTypeSupported(Class<?> type) {
		return type == WorldTime.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parseArgument(Class<T> type, String arg) throws ArgumentException, IllegalArgumentException {
		if (arg.equalsIgnoreCase("dawn")) return (T) WorldTime.DAWN;
		if (arg.equalsIgnoreCase("day")) return (T) WorldTime.DAY;
		if (arg.equalsIgnoreCase("dusk")) return (T) WorldTime.DUSK;
		if (arg.equalsIgnoreCase("midnight")) return (T) WorldTime.MIDNIGHT;
		if (arg.equalsIgnoreCase("night")) return (T) WorldTime.NIGHT;
		if (arg.equalsIgnoreCase("noon")) return (T) WorldTime.NOON;
		
		if (arg.endsWith("am")){
			arg = arg.substring(0, arg.length()-2);
			return (T) new WorldTime(new Integer(arg));
		}
		
		if (arg.endsWith("pm")){
			arg = arg.substring(0, arg.length()-2);
			return (T) new WorldTime(new Integer(arg) + 12);
		}
		
		try{
			return (T) new WorldTime(new Integer(arg));
		} catch (NumberFormatException ex){ throw getArgumentException(type, arg); }
	}
	
	@Override
	public List<String> getAllSuggestions(Class<?> type, String prefix) throws IllegalArgumentException {
		checkTypeSupported(type);
		
		return Lists.newArrayList("day", "dawn", "night", "dusk", "midnight", "noon");
	}
	
}
