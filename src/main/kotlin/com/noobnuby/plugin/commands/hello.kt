package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main
import io.github.monun.kommand.kommand.PluginKommand
import io.github.monun.kommand.kommand

object Hello {
    fun register(kommand: PluginKommand) {
        kommand.register("hello") {
            executes {
                sender.sendMessage("Hello, world!")
            }
        }
    }
}