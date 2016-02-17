package uk.co.terragaming.TerraEssentials.listeners;

import java.util.Objects;

import javax.inject.Inject;

import org.spongepowered.api.data.manipulator.mutable.entity.JoinData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import uk.co.terragaming.TerraEssentials.config.EssentialsData;

public class PlayerJoinListener {
	
	@Inject
	EssentialsData data;
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event){
		Player player = event.getTargetEntity();
		
		if (player.get(JoinData.class).isPresent() && player.getJoinData().firstPlayed().get().equals(player.getJoinData().lastPlayed().get())){
			Location<World> spawn = data.spawn.getLocation();
			
			if (spawn != null){
				if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId())){
					player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
				} else {
					player.setLocation(spawn);
				}
			}
		}
	}
}
