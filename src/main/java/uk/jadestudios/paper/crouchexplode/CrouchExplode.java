package uk.jadestudios.paper.crouchexplode;

import com.destroystokyo.paper.ParticleBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrouchExplode extends JavaPlugin implements Listener {

    private double chance;
    private Player lastPlayer;
    //TODO: May not be iosafe since last player might not be new instances

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("[CrouchExplode] Starting up!");
        this.saveDefaultConfig();
        chance = this.getConfig().getDouble("chance");

        if(chance < 0 || chance > 1){
            this.getServer().getConsoleSender().sendMessage("[CrouchExplode] Invalid chance value - Using default value of 0.5");
            chance = 0.5;
        }

        chance = 1 - chance;
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @EventHandler
     public void onCrouch(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            if(Math.random() > chance){
                lastPlayer = event.getPlayer();
                ParticleBuilder explosion = new ParticleBuilder(Particle.EXPLOSION_LARGE);
                explosion.count(9);
                explosion.location(lastPlayer.getLocation());
                explosion.spawn();
                lastPlayer.setHealth(0);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(this.lastPlayer == null)return;
        Player currentPlayer = event.getEntity();
        if (this.lastPlayer.equals(currentPlayer)){
            event.setDeathMessage(currentPlayer.getName() + " died to explosive diarrhea");
            event.setDeathSound(Sound.ENTITY_GENERIC_EXPLODE);
            this.lastPlayer = null;
        }

    }


}
