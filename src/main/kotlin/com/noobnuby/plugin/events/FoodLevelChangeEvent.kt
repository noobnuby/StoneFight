package com.noobnuby.plugin.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodLevelChangeEvent:Listener {
    @EventHandler
    fun onFoodLevelChange(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}