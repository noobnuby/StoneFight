package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class DeathEvent:Listener {
    @EventHandler
    fun onDeathEvent(e:PlayerDeathEvent) {
        val p = e.player
        val inventory = p.inventory
        val items = inventory.contents
        var sum = 0

        items.forEach { item ->
            if (item != null && item.type == Material.COBBLESTONE) {
                sum += item.amount
            }
        }

        val removeAmount = (sum * 0.5).toInt()

        var removedAmount = 0
        for (item in items) {
            if (item != null && item.type == Material.COBBLESTONE) {
                val currentAmount = item.amount
                if (currentAmount <= removeAmount - removedAmount) {
                    removedAmount += currentAmount
                    item.amount = 0
                } else {
                    item.amount = currentAmount - (removeAmount - removedAmount)
                    break
                }
            }
        }

        p.gameMode = GameMode.SPECTATOR
        Main.PlayerRespawnTime.put(p,10*20)
    }
}