package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main.Companion.isGameStart
import com.noobnuby.plugin.team.TeamCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveEvent:Listener {
    @EventHandler
    fun onPlayerMove(e:PlayerMoveEvent) {
        val p = e.player

        if(p.location.y < -10) {
            p.damage(10000000000.0)
        }

        if (isGameStart) {
            if (p.location.distance(TeamCore.RED_CORE) <= 30) {
                p.showBossBar(TeamCore.bossbarRed)
            } else {
                p.hideBossBar(TeamCore.bossbarRed)
            }
            if (p.location.distance(TeamCore.BLUE_CORE) <= 30) {
                p.showBossBar(TeamCore.bossbarBlue)
            } else {
                p.hideBossBar(TeamCore.bossbarBlue)
            }
        }
    }
}