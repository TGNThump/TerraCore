package uk.co.terragaming.TerraCore.Config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import uk.co.terragaming.TerraCore.Enums.ServerMode;

public class MainConfig extends ConfigBase{
	
	public MainConfig() {
		super("config.conf");
	}

	@Override
	public void setDefaults(){
		server = setDefault(server, new ServerCategory());
		server.name = setDefault(server.name, "DEV0");
		server.ip = setDefault(server.ip, "DEV0.Terra-Craft.net");
		server.mode = setDefault(server.mode, ServerMode.DEVELOPMENT);
		
		database = setDefault(database, new DatabaseCategory());
		database.database = setDefault(database.database, "");
		database.hostname = setDefault(database.hostname, "");
		database.port = setDefault(database.port, 3306);
		database.username = setDefault(database.username, "");
		database.password = setDefault(database.password, "");
	}
	
	@Setting
	public ServerCategory server;
	
	@Setting
	public DatabaseCategory database;
	
	@ConfigSerializable
	public static class ServerCategory extends ConfigBase.Category{
		@Setting
		public String name;
		
		@Setting
		public String ip;

		@Setting
		public ServerMode mode;
	}
	
	@ConfigSerializable
	public static class DatabaseCategory extends ConfigBase.Category{
		@Setting
		public String hostname;
		
		@Setting
		public String database;
		
		@Setting
		public Integer port;
		
		@Setting
		public String username;
		
		@Setting
		public String password;
	}
}
