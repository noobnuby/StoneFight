package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main.Companion.isGameStart
import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore.BLUE_SPAWN
import com.noobnuby.plugin.team.TeamCore.RED_SPAWN
import com.noobnuby.plugin.team.TeamCore.playerTeam
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import xyz.icetang.lib.kommand.PluginKommand

object StoneFight {
    fun register(kommand: PluginKommand) {
        kommand.register("sf") {
            requires { isOp&&isPlayer&& Bukkit.getOnlinePlayers().size <= 4 }
            then("start") {
                executes {
                    val size = Bukkit.getOnlinePlayers().size
                    var players = 0
                    isGameStart = true

                    val shuffledPlayers = Bukkit.getOnlinePlayers().shuffled()

                    val manager = Bukkit.getScoreboardManager()
                    val board = manager.mainScoreboard

                    var red = board.getTeam("RED")
                    var blue = board.getTeam("BLUE")
                    if (red == null&&blue == null) {

                        red = board.registerNewTeam("RED")
                        red.color(NamedTextColor.RED)
                        blue = board.registerNewTeam("BLUE")
                        blue.color(NamedTextColor.BLUE)

                    }else {

                        red?.unregister()
                        blue?.unregister()
                        red = board.registerNewTeam("RED")
                        red.color(NamedTextColor.RED)
                        blue = board.registerNewTeam("BLUE")
                        blue.color(NamedTextColor.BLUE)

                    }

                    shuffledPlayers.forEach {
                        if (players < size / 2) {

                            playerTeam[it] = Team.RED
                            it.teleport(RED_SPAWN)
                            red.addPlayer(it)

                        } else {

                            playerTeam[it] = Team.BLUE
                            it.teleport(BLUE_SPAWN)
                            blue.addPlayer(it)

                        }
                        players++
                    }
                }
            }
        }
    }
}