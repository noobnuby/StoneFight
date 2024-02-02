package com.noobnuby.plugin.events

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent


class BlockBreakEvent:Listener {
    @EventHandler
    fun onBlockBreakEvent(e: BlockBreakEvent) {
        val p = e.player
        val hand = p.inventory.itemInMainHand
        if (hand.type == Material.STONE_PICKAXE && e.block.type == Material.COBBLESTONE && p.gameMode == GameMode.SURVIVAL) {
            e.isDropItems = false
            for (i in e.block.drops) {
                i.amount = i.amount * 10
                e.block.world.dropItemNaturally(e.block.location, i)
            }
        }
    }
}