package com.noobnuby.plugin.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class LeftClickEvent:Listener {
    @EventHandler
    fun onLeftClick(e:PlayerInteractEvent) {
        val item = e.item

        if (item != null && item.type == Material.COBBLESTONE && e.action == Action.LEFT_CLICK_AIR) {
            item.amount -= 1
        }
    }
}