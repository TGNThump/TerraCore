package uk.co.terragaming.TerraEssentials.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import uk.co.terragaming.TerraCore.Config.ConfigBase;

import com.flowpowered.math.vector.Vector3i;

public class EssentialsData extends ConfigBase{

	public EssentialsData() {
		super("data", "essentials.conf");
	}
	
	@Override
	public void setDefaults() {
		
		WorldProperties world = Sponge.getServer().getDefaultWorld().get();
		
		Vector3i spawnPos = world.getSpawnPosition();
		
		spawn = setDefault(spawn, new WorldLocation());
		
		spawn.x = setDefault(spawn.x, (double) spawnPos.getX());
		spawn.y = setDefault(spawn.y, (double) spawnPos.getY());
		spawn.z = setDefault(spawn.z, (double) spawnPos.getZ());
		spawn.worldUUID = setDefault(spawn.worldUUID, world.getUniqueId());
		
		homes = setDefault(homes, new HashMap<UUID, WorldLocation>());
	}
	
	@Setting
	public WorldLocation spawn;
	
	
	@Setting
	public Map<UUID, WorldLocation> homes;
	
	@ConfigSerializable
	public static class WorldLocation extends ConfigBase.Category{
		
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
			Optional<World> world = Sponge.getServer().getWorld(worldUUID);
			if (world.isPresent()){
				return new Location<World>(world.get(), x, y, z);
			}
			
			return Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorld().get().getUniqueId()).get().getSpawnLocation();
		}
	}
	
}
