package com.noobnuby.plugin

import com.noobnuby.plugin.commands.Hello
import com.noobnuby.plugin.events.JoinQuit
import org.bukkit.plugin.java.JavaPlugin
import io.github.monun.kommand.kommand

class Main : JavaPlugin() {
    companion object { lateinit var INSTANCE: Main }

    override fun onEnable() {
        INSTANCE = this

        logger.info("Enable Plugin!")

        kommand {
            Hello.register(this)
        }

        server.pluginManager.apply {
            registerEvents(JoinQuit(),this@Main)
        }
    }
}