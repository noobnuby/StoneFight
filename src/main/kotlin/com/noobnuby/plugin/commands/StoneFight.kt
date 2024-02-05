package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main.Companion.isGameStart
import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore.BLUE_SPAWN
import com.noobnuby.plugin.team.TeamCore.RED_SPAWN
import com.noobnuby.plugin.team.TeamCore.playerTeam
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.icetang.lib.kommand.PluginKommand

object StoneFight {
    val manager = Bukkit.getScoreboardManager()
    val board = manager.mainScoreboard

    lateinit var red: org.bukkit.scoreboard.Team
    lateinit var blue: org.bukkit.scoreboard.Team

    private var setting = false

    fun setUpTeams() {
        if (board.getTeam("RED") != null) {
            board.getTeam("RED")!!.unregister()
        }
        red = board.registerNewTeam("RED").apply {
            color(NamedTextColor.RED)
        }

        if (board.getTeam("BLUE") != null) {
            board.getTeam("BLUE")!!.unregister()
        }
        blue = board.registerNewTeam("BLUE").apply {
            color(NamedTextColor.BLUE)
        }
    }
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