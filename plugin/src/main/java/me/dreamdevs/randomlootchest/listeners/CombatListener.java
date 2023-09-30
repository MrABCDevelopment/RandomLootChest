package me.dreamdevs.randomlootchest.listeners;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class CombatListener implements Listener {

    @EventHandler
    public void combatEvent(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            RandomLootChestMain.getInstance().getCombatManager().applyCombat(Objects.requireNonNull(((Player) event.getDamager()).getPlayer()), Config.COMBAT_BLOCKER_TIMER.toInt());
            RandomLootChestMain.getInstance().getCombatManager().applyCombat(Objects.requireNonNull(((Player) event.getEntity()).getPlayer()), Config.COMBAT_BLOCKER_TIMER.toInt());
        }
    }

}