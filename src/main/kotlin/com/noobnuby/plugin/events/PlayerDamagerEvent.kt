package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.team.TeamCore.playerTeam
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PlayerDamagerEvent:Listener {
    @EventHandler
    fun onPlayerDamager(e:EntityDamageByEntityEvent) {
        if (!Main.isGameStart) {
            if (e.entity is Player && e.damager is Player) {
                e.isCancelled = true
            }
        }

        if (e.entity is Player && e.damager is Player) {
            val p = e.entity as Player
            val damager = e.damager as Player

            if (playerTeam[p] == playerTeam[damager]) {
                e.isCancelled = true
            }
        }
    }
}
