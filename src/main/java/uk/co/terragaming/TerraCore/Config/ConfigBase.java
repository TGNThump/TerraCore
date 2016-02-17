package uk.co.terragaming.TerraCore.Config;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import uk.co.terragaming.TerraCore.TerraPlugin;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;


public abstract class ConfigBase {
	
	private File file;
	private CommentedConfigurationNode config;
	
	private ObjectMapper<ConfigBase>.BoundInstance configMapper;
	private ConfigurationLoader<CommentedConfigurationNode> loader;
	
	private TerraLogger logger = TerraPlugin.instance.logger;
	
	public ConfigBase(String folder, String configName){
		folder = "config/" + TerraPlugin.getPluginContainer().getName().toLowerCase() + "/" + folder + "/";
		 
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
	
	public ConfigBase(String configName){
		String folder = "config/" + TerraPlugin.getPluginContainer().getName().toLowerCase() + "/";
		 
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
	
	public ConfigBase(){
		String folder = "config/" + TerraPlugin.getPluginContainer().getName().toLowerCase() + "/";
		 
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
		
    }
	
}
