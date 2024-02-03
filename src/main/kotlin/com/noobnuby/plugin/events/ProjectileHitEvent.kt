package com.noobnuby.plugin.events

import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore
import com.noobnuby.plugin.team.TeamCore.playerTeam
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack

class ProjectileHitEvent : Listener {
    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        if (e.entity != null && (e.entity as Snowball).item.type == Material.COBBLESTONE && e.entity.shooter is Player) {
            val stone = e.entity as Snowball
            if (e.hitEntity is LivingEntity) {
                val entity = e.hitEntity as LivingEntity
                entity.noDamageTicks = 0
                entity.damage(1.0, stone.shooter as Player)

                stone.world.spawnParticle(
                    Particle.BLOCK_CRACK,
                    stone.location,
                    20,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    Material.COBBLESTONE.createBlockData(),
                    true
                )
                stone.world.playSound(stone.location, Sound.BLOCK_STONE_BREAK, 1f, 1f)
            } else if (e.hitBlock != null && e.hitBlock is Block) {
                val block = e.hitBlock!!
                if (block.type == Material.COBBLESTONE) {
                    val random = (Math.random() * 100 + 1).toInt()
                    if (random < 10) {
                        block.breakNaturally(ItemStack(Material.NETHERITE_PICKAXE))
                    }
                }
                if (block.location == TeamCore.RED_CORE) {
                    if (playerTeam[e.entity.shooter] == Team.RED) return
                }
                if (block.location == TeamCore.BLUE_CORE) {
                    if (playerTeam[e.entity.shooter] == Team.BLUE) return
                }
                stone.world.spawnParticle(
                    Particle.BLOCK_CRACK,
                    stone.location,
                    20,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    Material.COBBLESTONE.createBlockData(),
                    true
                )
                stone.world.playSound(stone.location, Sound.BLOCK_STONE_BREAK, 1f, 1f)
            }
        }
    }
}