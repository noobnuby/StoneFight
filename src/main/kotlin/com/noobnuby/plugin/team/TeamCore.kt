package com.noobnuby.plugin.team

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object TeamCore {
    var RED_CORE_HP = 200
    var BLUE_CORE_HP = 200

    val playerTeam: MutableMap<Player, Team> = mutableMapOf()

    val bossbarRed: BossBar = BossBar.bossBar(Component.text("RED",NamedTextColor.RED),0.0F, BossBar.Color.RED,BossBar.Overlay.PROGRESS)

    val bossbarBlue: BossBar = BossBar.bossBar(Component.text("BLUE",NamedTextColor.BLUE),0.0F, BossBar.Color.BLUE,BossBar.Overlay.PROGRESS)

    val BLUE_SPAWN = Location(Bukkit.getWorld("world"),0.5, 53.0, 41.5,-180F,0F)

    val RED_SPAWN = Location(Bukkit.getWorld("world"), 0.5, 53.0, -41.5)

    val BLUE_CORE = Location(Bukkit.getWorld("world"), 0.0, 62.0, 53.0)

    val RED_CORE = Location(Bukkit.getWorld("world"), 0.0, 62.0, -53.0)
}