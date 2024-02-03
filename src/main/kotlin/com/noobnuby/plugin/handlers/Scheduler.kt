package com.noobnuby.plugin.handlers

import com.noobnuby.plugin.ItemDisplayData
import com.noobnuby.plugin.Main
import com.noobnuby.plugin.Main.Companion.PlayerRespawnTime
import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore.BLUE_SPAWN
import com.noobnuby.plugin.team.TeamCore.RED_SPAWN
import com.noobnuby.plugin.team.TeamCore.playerTeam
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode

object Scheduler {
    private var scheduleId: Int? = null

    fun start() {
        scheduleId = Main.INSTANCE.server.scheduler.runTaskTimer(Main.INSTANCE, Runnable {

            PlayerRespawnTime.forEach {
                val p = it.key
                val timer = it.value

                val updatedTimer = timer - 1
                PlayerRespawnTime[p] = updatedTimer

                val timeInSeconds = updatedTimer / 20.0
                val formattedTime = String.format("%.1f", timeInSeconds)

                val actionBarText = Component.text("부활까지 남은 시간: ")
                    .color(NamedTextColor.GREEN)
                    .append(Component.text(formattedTime, NamedTextColor.YELLOW))

                p.sendActionBar(actionBarText)

                if (PlayerRespawnTime[p]!! == 0) {
                    PlayerRespawnTime.remove(p)
                    p.sendActionBar(Component.text("부활함!", NamedTextColor.YELLOW))
                    p.gameMode = GameMode.SURVIVAL
                    if (playerTeam[p] == Team.RED) {
                        p.teleport(RED_SPAWN)
                    } else {
                        p.teleport(BLUE_SPAWN)
                    }
                }
            }
        }, 0L, 1L).taskId
    }
}