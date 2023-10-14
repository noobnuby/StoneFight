package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main
import xyz.icetang.lib.kommand.PluginKommand
import xyz.icetang.lib.kommand.kommand

object Hello {
    fun register(kommand: PluginKommand) {
        kommand.register("hello") {
            executes {
                sender.sendMessage("Hello, world!")
            }
        }
    }
}