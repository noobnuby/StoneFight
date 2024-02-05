package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main.Companion.isGameStart
import com.noobnuby.plugin.team.Team
import com.noobnuby.plugin.team.TeamCore
import com.noobnuby.plugin.team.TeamCore.playerTeam
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
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
        if ((e.entity as Snowball).item.type == Material.COBBLESTONE && e.entity.shooter is Player) {
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
                val random = (Math.random() * 100 + 1).toInt()
                if (random < 10) {
                    block.breakNaturally(ItemStack(Material.NETHERITE_PICKAXE))
                }
                if (block.location == TeamCore.RED_CORE) {
                    if (playerTeam[e.entity.shooter] == Team.RED) return
                    TeamCore.RED_CORE_HP -= 1
                    Bukkit.getOnlinePlayers().forEach {
                        if (playerTeam[it] == Team.RED) it.sendMessage(
                            Component.text(
                                "코어가 공격당하고 있습니다!",
                                NamedTextColor.RED
                            )
                        )
                    }
                    if (TeamCore.RED_CORE_HP == 0) {
                        isGameStart = false
                        Bukkit.getOnlinePlayers().forEach {
                            if (playerTeam[it] == Team.BLUE) it.playSound(
                                it.location,
                                Sound.UI_TOAST_CHALLENGE_COMPLETE,
                                1f,
                                1f
                            )
                            it.showTitle(
                                Title.title(
                                    MiniMessage.miniMessage().deserialize("<blue>블루팀<white> 승리!"),
                                    Component.empty()
                                )
                            )
                            it.hideBossBar(TeamCore.bossbarRed)
                            it.hideBossBar(TeamCore.bossbarBlue)
                        }
                    }
                }
                if (block.location == TeamCore.BLUE_CORE) {
                    if (playerTeam[e.entity.shooter] == Team.BLUE) return
                    TeamCore.BLUE_CORE_HP -= 1
                    Bukkit.getOnlinePlayers().forEach {
                        if (playerTeam[it] == Team.BLUE) it.sendMessage(
                            Component.text(
                                "코어가 공격당하고 있습니다!",
                                NamedTextColor.RED
                            )
                        )
                    }
                    if (TeamCore.BLUE_CORE_HP == 0) {
                        isGameStart = false
                        Bukkit.getOnlinePlayers().forEach {
                            it.playSound(it.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f)
                            it.showTitle(
                                Title.title(
                                    MiniMessage.miniMessage().deserialize("<red>레드팀<white> 승리!"),
                                    Component.empty()
                                )
                            )
                            it.hideBossBar(TeamCore.bossbarRed)
                            it.hideBossBar(TeamCore.bossbarBlue)
                        }
                    }
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