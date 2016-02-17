package uk.co.terragaming.TerraCore.Util;


public class WorldTime {
	
	public static WorldTime MIDNIGHT = new WorldTime(0);
	public static WorldTime DAWN = new WorldTime(6);
	public static WorldTime DAY = new WorldTime(7);
	public static WorldTime NOON = new WorldTime(12);
	public static WorldTime DUSK = new WorldTime(18);
	public static WorldTime NIGHT = new WorldTime(20);
	
	private long time;
	
	public long getTickTime(){
		return time;
	}
	
	public int get24Hour(){
		int hour = (int) Math.floor(time / 1000);
		
		hour = hour + 6;
		if (hour == 24) return 0;
		if (hour > 24) return hour - 24;
		return hour;
	}
	
	public String get12Hour(){
		int hour = get24Hour();
		if (hour == 0) hour = 24;
		if (hour < 13) return hour + "am";
		else if (hour < 24) return (hour % 12) + "pm";
		else return "12pm";
	}
	
	public WorldTime(long ticks){
		time = ticks % 24000;
	}
	
	public WorldTime(int hour){
		if (hour < 0) throw new IllegalArgumentException();
		if (hour > 24) hour = hour % 24;
		hour = hour - 6;
		if (hour < 0){
			hour = 24 + hour;
		}
		
		time = (long) hour * 1000;
	}
	
}
