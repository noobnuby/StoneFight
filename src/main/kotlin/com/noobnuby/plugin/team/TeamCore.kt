package com.noobnuby.plugin.team

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.Listener
import java.util.*
import kotlin.collections.HashMap

object TeamCore : Listener {
    var RED_CORE_HP = 200
    var BLUE_CORE_HP = 200

    val playerTeram: HashMap<UUID, String> = HashMap()

    val bossbarRed: BossBar = BossBar.bossBar(Component.text("RED",NamedTextColor.RED),200F, BossBar.Color.RED,BossBar.Overlay.PROGRESS)

    val bossbarBlue: BossBar = BossBar.bossBar(Component.text("RED",NamedTextColor.RED),200F, BossBar.Color.RED,BossBar.Overlay.PROGRESS)
}