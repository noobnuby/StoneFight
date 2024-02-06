package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.team.TeamCore.playerTeam
import xyz.icetang.lib.kommand.PluginKommand

object Test {
    fun register(kommand: PluginKommand) {
        kommand.register("test") {
            requires { isOp && isPlayer }
            executes {
                sender.sendMessage("${Main.PlayerRespawnTime}")
            }
        }
    }
}