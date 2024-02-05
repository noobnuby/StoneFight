package com.noobnuby.plugin.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceEvent:Listener {
    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val blockAgainst = e.blockReplacedState
        val type = blockAgainst.type

        if (type == Material.WATER || type == Material.LAVA) {
            e.isCancelled = true
        }
    }
}