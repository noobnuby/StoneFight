package com.noobnuby.plugin.commands

import xyz.icetang.lib.kommand.PluginKommand

object Hello {
    fun register(kommand: PluginKommand) {
        kommand.register("hello") {
            executes {
                sender.sendMessage("Hello, world!")
            }
        }
    }
}