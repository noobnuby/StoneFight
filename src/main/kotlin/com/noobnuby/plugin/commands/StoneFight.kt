package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main.Companion.isGameStart
import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore.BLUE_SPAWN
import com.noobnuby.plugin.team.TeamCore.RED_SPAWN
import com.noobnuby.plugin.team.TeamCore.playerTeam
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.icetang.lib.kommand.PluginKommand
import xyz.icetang.lib.kommand.getValue

object StoneFight {
    val manager = Bukkit.getScoreboardManager()
    val board = manager.mainScoreboard

    val red = board.getTeam("RED")!!
    val blue = board.getTeam("BLUE")!!

    private var setting = false
    fun register(kommand: PluginKommand) {
        kommand.register("sf") {
            requires { isOp&&isPlayer}
            then("start") {
                executes {
                    isGameStart = true
                    if (!setting) {
                        var players = 0
                        val size = Bukkit.getOnlinePlayers().size

                        val shuffledPlayers = Bukkit.getOnlinePlayers().shuffled()

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
                    } else {
                        Bukkit.getOnlinePlayers().forEach {
                            if (playerTeam[it] == Team.RED) {
                                it.teleport(RED_SPAWN)
                            } else {
                                it.teleport(BLUE_SPAWN)
                            }
                        }
                    }
                }
            }
            then("setting") {
                then("RED") {
                    then("player" to player()) {
                        executes {
                            setting = true
                            val p: Player = it["player"]
                            playerTeam[p] = Team.RED
                            red.addPlayer(p)
                        }
                    }
                }
                then("BLUE") {
                    then("player" to player()) {
                        executes {
                            setting = true
                            val p: Player = it["player"]
                            playerTeam[p] = Team.BLUE
                            blue.addPlayer(p)
                        }
                    }
                }
            }
        }
    }
}