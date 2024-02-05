package com.noobnuby.plugin.events

import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore
import com.noobnuby.plugin.team.TeamCore.playerTeam
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent

class BucketEvent:Listener {
    @EventHandler
    fun onBukkitFill(e:PlayerBucketFillEvent) {
        val p = e.player
        e.isCancelled = true
        if (p.location.distance(TeamCore.BLUE_SPAWN) <= 10&&playerTeam[p] == Team.BLUE) {
            e.isCancelled = false
        }
        if (p.location.distance(TeamCore.RED_SPAWN) <= 10&&playerTeam[p] == Team.RED) {
            e.isCancelled = false
        }
    }

    @EventHandler
    fun onBukkitEmpty(e:PlayerBucketEmptyEvent) {
        val p = e.player
        e.isCancelled = true
        if (p.location.distance(TeamCore.BLUE_SPAWN) <= 10&&playerTeam[p] == Team.BLUE) {
            e.isCancelled = false
        }
        if (p.location.distance(TeamCore.RED_SPAWN) <= 10&&playerTeam[p] == Team.RED) {
            e.isCancelled = false
        }
    }
}