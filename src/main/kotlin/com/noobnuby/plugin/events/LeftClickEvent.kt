package com.noobnuby.plugin.events

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.data.BlockData
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Vector3f

class LeftClickEvent:Listener {
    @EventHandler
    fun onLeftClick(e:PlayerInteractEvent) {
        val item = e.item
        val p = e.player
        if (item != null && item.type == Material.COBBLESTONE && e.action == Action.LEFT_CLICK_AIR&&p.getCooldown(Material.COBBLESTONE) == 0) {
            item.amount -= 1
            p.playSound(p.location, Sound.ENTITY_ARROW_SHOOT, 0.5f, 0.5f)
            val stone = p.world.spawn(p.eyeLocation, Snowball::class.java)
            stone.shooter = p
            stone.item = ItemStack(Material.COBBLESTONE)
            val direction = p.eyeLocation.direction.normalize().multiply(1.1)

            direction.y *= 1
            stone.velocity = direction
            p.setCooldown(Material.COBBLESTONE, 2)

            if(item.amount == 0) {
                for(i in 0..35) {
                    if(p.inventory.getItem(i) != null && p.inventory.getItem(i)!!.type == Material.COBBLESTONE) {
                        val stoneItem = p.inventory.getItem(i)!!
                        p.inventory.setItem(i, ItemStack(Material.AIR))
                        p.inventory.setItemInMainHand(stoneItem)
                        break
                    }
                }
            }
        }
    }
}