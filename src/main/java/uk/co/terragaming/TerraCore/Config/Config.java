package uk.co.terragaming.TerraCore.Config;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.spongepowered.api.Server;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import uk.co.terragaming.TerraCore.CorePlugin;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;


public abstract class Config {
	
	private File file;
	private CommentedConfigurationNode config;
	
	private ObjectMapper<Config>.BoundInstance configMapper;
	private ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private TerraLogger logger = CorePlugin.instance().getLogger();
	
	private void injectConfig(){
		if (CorePlugin.instance().getInjector() == null){
			CorePlugin.instance().getBaseInjector().injectMembers(this);
		} else {
			CorePlugin.instance().getInjector().injectMembers(this);
		}
	}
	
	public Config(String folder, String configName){
		injectConfig();
		folder = "config/" + CorePlugin.NAME.toLowerCase() + "/" + folder + "/";
		 
		if (!new File(folder).isDirectory()){
			new File(folder).mkdirs();
		}
		 
		file = new File(folder + configName);
		 
		create();
		init();
		load();
		setDefaults();
		save();
	}
	
	public Config(String configName){
		injectConfig();
		String folder = "config/" + CorePlugin.NAME.toLowerCase() + "/";
		 
		if (!new File(folder).isDirectory()){
			new File(folder).mkdirs();
		}
		 
		file = new File(folder + configName);
		 
		create();
		init();
		load();
		setDefaults();
		save();
	}
	
	public Config(){
		injectConfig();
		String folder = "config/" + CorePlugin.NAME.toLowerCase() + "/";
		 
		if (!new File(folder).isDirectory()){
			new File(folder).mkdirs();
		 }
		 
		file = new File(folder + "config.conf");
		 
		create();
		init();
		load();
		setDefaults();
		save();
	}
	
	private void init(){
		try{
			this.configMapper = ObjectMapper.forObject(this);
		} catch (ObjectMappingException e){
			e.printStackTrace();
		}
	}
	
	private void create(){
		if (!file.exists()){
			if (logger != null) logger.info("<l>Creating new <h>" + file.getName() + "<l> file...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				if (logger != null) logger.info("<b>Failed to create new config file named <h>" + file.getName() + "<b>.");
				e.printStackTrace();
			}
		}
	}
	
	public void save(){
		try {
			SimpleConfigurationNode out = SimpleConfigurationNode.root();
			this.configMapper.serialize(out);
			this.loader.save(out);
		} catch (ObjectMappingException |IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load(){
		this.loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = this.loader.load();
			this.configMapper.populate(config);
		} catch (ObjectMappingException | IOException e) {
			if (logger != null) logger.error("<b>Failed to load config file named <h>" + file.getName() + "<b>.");
			e.printStackTrace();
		}
	}
	
	public abstract void setDefaults();
	
	public <T> T setDefault(T param, T value){
		if (param == null) return value;
		return param;
	}
	
	@ConfigSerializable
	protected static class Category {
		
		private void injectConfig(){
			if (CorePlugin.instance().getInjector() == null){
				CorePlugin.instance().getBaseInjector().injectMembers(this);
			} else {
				CorePlugin.instance().injector.injectMembers(this);
			}
		}
		
		protected Category(){
			injectConfig();
		}
		
    }
	
	@ConfigSerializable
	public static class WorldLocation extends Config.Category{
		
		@Inject
		Server server;
		
		@Setting
		public Double x;
		
		@Setting
		public Double y;
		
		@Setting
		public Double z;
		
		@Setting("world-uuid")
		public UUID worldUUID;
		
		public WorldLocation(){}
		
		public WorldLocation(Location<World> loc){
			set(loc);
		}
		
		public void set(Location<World> loc){
			this.x = loc.getX();
			this.y = loc.getY();
			this.z = loc.getZ();
			this.worldUUID = loc.getExtent().getUniqueId();
		}
		
		public Location<World> get(){
			Optional<World> world = server.getWorld(worldUUID);
			if (world.isPresent()){
				return new Location<World>(world.get(), x, y, z);
			}
			
			return server.getWorld(server.getDefaultWorld().get().getUniqueId()).get().getSpawnLocation();
		}
	}
	
}
