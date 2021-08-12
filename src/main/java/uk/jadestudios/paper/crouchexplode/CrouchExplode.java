package uk.jadestudios.paper.crouchexplode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrouchExplode extends JavaPlugin implements Listener {

    private double chance;

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("[CrouchExplode] Starting up!");
        this.saveDefaultConfig();
        chance = this.getConfig().getDouble("chance");

        if(chance < 0 || chance > 1){
            this.getServer().getConsoleSender().sendMessage("[CrouchExplode] Invalid chance value - Using default value of 0.5");
            chance = 0.5;
        }

        chance = 1 - chance; //Since 1 means always in the config
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event){
        if(event.isSneaking() && Math.random() > this.chance){
            new PerPlayerExplode(event.getPlayer(), this.getServer(), this);
        }
    }
}
