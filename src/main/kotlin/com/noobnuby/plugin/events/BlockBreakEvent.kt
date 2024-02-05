package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main.Companion.isGameStart
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector


class BlockBreakEvent:Listener {
    @EventHandler
    fun onBlockBreakEvent(e: BlockBreakEvent) {
        val p = e.player
        if (!isGameStart) {
            if (p.isOp) return
            e.isCancelled = true
        }
        val hand = p.inventory.itemInMainHand
        if (hand.type == Material.STONE_PICKAXE && e.block.type == Material.COBBLESTONE && p.gameMode == GameMode.SURVIVAL && e.block.getSideBlocksType().contains(Material.LAVA) && e.block.getSideBlocksType().contains(Material.WATER)) {
            e.isDropItems = false
            for(i in 0..9) {
                val item = e.block.world.dropItemNaturally(e.block.location, ItemStack(Material.COBBLESTONE,1))
                item.velocity = Vector((Math.random()*0.2)-0.1, 0.1, (Math.random()*0.2)-0.1)
            }
        }


    }

    fun Block.getSideBlocksType() : MutableList<Material> {
        val block = this
        val location = block.location
        val blocks = mutableListOf<Material>()

        blocks.add(location.clone().add(1.0,0.0,0.0).block.type)
        blocks.add(location.clone().add(-1.0,0.0,0.0).block.type)
        blocks.add(location.clone().add(0.0,0.0,1.0).block.type)
        blocks.add(location.clone().add(0.0,0.0,-1.0).block.type)
        return blocks
    }
}