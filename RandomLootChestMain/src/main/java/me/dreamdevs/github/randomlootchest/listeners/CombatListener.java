package me.dreamdevs.github.randomlootchest.listeners;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.utils.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CombatListener implements Listener {

    @EventHandler
    public void combatEvent(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            RandomLootChestMain.getInstance().getCombatManager().applyCombat(((Player) event.getDamager()).getPlayer(), Settings.combatTime);
            RandomLootChestMain.getInstance().getCombatManager().applyCombat(((Player) event.getEntity()).getPlayer(), Settings.combatTime);
        }
    }

}