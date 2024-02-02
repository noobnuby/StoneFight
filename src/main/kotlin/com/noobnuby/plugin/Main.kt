package com.noobnuby.plugin

import com.noobnuby.plugin.commands.Hello
import com.noobnuby.plugin.events.BlockBreakEvent
import com.noobnuby.plugin.events.DeathEvent
import com.noobnuby.plugin.events.LeftClickEvent
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand

class Main : JavaPlugin() {
    companion object { lateinit var INSTANCE: Main }

    override fun onEnable() {
        INSTANCE = this

        logger.info("Enable Plugin!")

        kommand {
            Hello.register(this)
        }

        val world = Bukkit.getWorld("world")!!
        world.setGameRule(GameRule.KEEP_INVENTORY, true)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)

        server.pluginManager.apply {
            registerEvents(BlockBreakEvent(),this@Main)
            registerEvents(DeathEvent(),this@Main)
            registerEvents(LeftClickEvent(),this@Main)
        }
    }
}