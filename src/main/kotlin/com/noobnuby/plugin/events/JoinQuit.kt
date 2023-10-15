package com.noobnuby.plugin.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinQuit: Listener {
    @EventHandler
    fun JoinQuite(e:PlayerJoinEvent) {
        val p = e.player
        e.joinMessage(Component.text("${p}님이 서버에 접속하셨습니다").color(NamedTextColor.YELLOW))
    }
}