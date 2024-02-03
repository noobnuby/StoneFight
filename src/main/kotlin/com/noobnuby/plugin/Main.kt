package com.noobnuby.plugin

import com.noobnuby.plugin.commands.StoneFight
import com.noobnuby.plugin.events.*
import com.noobnuby.plugin.handlers.Scheduler
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand

class Main : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: Main
        var isGameStart: Boolean = false
        var PlayerRespawnTime:MutableMap<Player,Int> = mutableMapOf()
    }

    override fun onEnable() {
        INSTANCE = this

        logger.info("Enable Plugin!")

        kommand {
            StoneFight.register(this)
        }

        val world = Bukkit.getWorld("world")!!
        world.setGameRule(GameRule.KEEP_INVENTORY, true)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)

        server.pluginManager.apply {
            registerEvents(BlockBreakEvent(),this@Main)
            registerEvents(DeathEvent(),this@Main)
            registerEvents(LeftClickEvent(),this@Main)
            registerEvents(PlayerDamagerEvent(),this@Main)
            registerEvents(ProjectileHitEvent(),this@Main)
        }

        Scheduler.start()
    }
}