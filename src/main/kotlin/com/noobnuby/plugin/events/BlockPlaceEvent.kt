package com.noobnuby.plugin.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceEvent:Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val block = event.blockAgainst
        val type = block.type

        if (type == Material.WATER || type == Material.LAVA) {
            event.isCancelled = true
        }
    }
}