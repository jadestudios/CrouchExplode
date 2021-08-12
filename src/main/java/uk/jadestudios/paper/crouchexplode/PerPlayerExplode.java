package uk.jadestudios.paper.crouchexplode;

import com.destroystokyo.paper.ParticleBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public final class PerPlayerExplode implements Listener {
    private Player lastPlayer;
    //TODO: ADD MORE DEATH MESSAGES

    /**
     * Creates an onDeath listener and explodes the player
     * @param player Player object
     * @param server Server object
     * @param plugin The current plugin
     */
    public PerPlayerExplode(Player player, Server server, Plugin plugin){
        this.lastPlayer = player;
        server.getPluginManager().registerEvents(this, plugin);
        explodeChance();
    }

    /**
     * Used to explode the player
     */
    private void explodeChance(){
        ParticleBuilder explosion = new ParticleBuilder(Particle.EXPLOSION_LARGE);
        explosion.count(9);
        explosion.location(this.lastPlayer.getLocation());
        explosion.spawn();
        this.lastPlayer.setHealth(0);
    }

    /**
     * Changes death message, plays explosion sound, and unregisters this listener
     * @param event PlayerDeathEvent
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player currentPlayer = event.getEntity();
        if (this.lastPlayer.equals(currentPlayer)){
            TextComponent text = Component.text().content(currentPlayer.getName()).append(Component.text(" died to explosive diarrhea")).build();
            event.deathMessage(text);
            currentPlayer.playSound(currentPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 100,0);
            this.lastPlayer = null;
            event.getHandlers().unregister(this);
        }
    }
}
