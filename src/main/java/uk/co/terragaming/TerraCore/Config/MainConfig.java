package uk.co.terragaming.TerraCore.Config;

import ninja.leaping.configurate.objectmapping.Setting;

public class MainConfig extends ConfigBase{
	
	public MainConfig() {
		super("config.conf");
	}

	@Override
	public void setDefaults(){
		devMode = setDefault(devMode, false);
//		database = setDefault(database, new DatabaseCategory());
//		database.database = setDefault(database.database, "");
//		database.hostname = setDefault(database.hostname, "");
//		database.port = setDefault(database.port, 3306);
//		database.username = setDefault(database.username, "");
//		database.password = setDefault(database.password, "");
	}
	
	@Setting
	public boolean devMode;
	
//	@Setting
//	public DatabaseCategory database;
//	
//	@ConfigSerializable
//	public static class DatabaseCategory extends ConfigBase.Category{
//		@Setting
//		public String hostname;
//		
//		@Setting
//		public String database;
//		
//		@Setting
//		public Integer port;
//		
//		@Setting
//		public String username;
//		
//		@Setting
//		public String password;
//	}
}
