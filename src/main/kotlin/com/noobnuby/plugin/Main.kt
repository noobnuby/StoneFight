package com.noobnuby.plugin

import com.noobnuby.plugin.commands.StoneFight
import com.noobnuby.plugin.events.*
import com.noobnuby.plugin.handlers.Scheduler
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.jar.JarFile

class Main : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: Main
        var isGameStart: Boolean = false
        var PlayerRespawnTime: MutableMap<Player, Int> = mutableMapOf()
    }

    override fun onEnable() {
        INSTANCE = this

        logger.info("Enable Plugin!")

        kommand {
            StoneFight.register(this)
        }

        val world = Bukkit.getWorld("world")!!
        world.setGameRule(GameRule.KEEP_INVENTORY, true)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        world.setSpawnLocation(Location(world, 0.5, 53.5, 0.5))

        server.pluginManager.apply {
            registerEvents(BlockBreakEvent(), this@Main)
            registerEvents(DeathEvent(), this@Main)
            registerEvents(LeftClickEvent(), this@Main)
            registerEvents(PlayerDamagerEvent(), this@Main)
            registerEvents(ProjectileHitEvent(), this@Main)
            registerEvents(PlayerMoveEvent(), this@Main)
            registerEvents(FoodLevelChangeEvent(), this@Main)
            registerEvents(BlockPlaceEvent(), this@Main)
            registerEvents(BucketEvent(), this@Main)
        }

        StoneFight.setUpTeams()
        Scheduler.start()
//        asdf()
    }

//    fun asdf() {
//        val worldResourcePath = "world"
//
//        if (Files.exists(dataFolder.toPath().resolve(worldResourcePath))) {
//            return
//        }
//
//        val jarFile = File(javaClass.protectionDomain.codeSource.location.toURI().path)
//        val jar = JarFile(jarFile)
//
//        val entries = jar.entries()
//        while (entries.hasMoreElements()) {
//            val entry = entries.nextElement()
//            if (entry.name.startsWith(worldResourcePath)) {
//                val file = File(dataFolder, entry.name)
//
//                if (entry.isDirectory) {
//                    file.mkdir()
//                } else {
//                    file.parentFile.mkdirs()
//
//                    // get the input stream
//                    jar.getInputStream(entry).use { input ->
//                        FileOutputStream(file).use { output ->
//                            while (input.available() > 0) {
//                                output.write(input.read())
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onDisable() {
//        val worldFolderPath = server.worldContainer.resolve("world").toPath() // 서버의 월드 데이터 경로
//        val copiedWorldPath = dataFolder.toPath().resolve("world")
//        Files.walk(worldFolderPath).sorted(Comparator.reverseOrder()).forEach(Files::delete)
//        Files.move(copiedWorldPath, worldFolderPath, StandardCopyOption.REPLACE_EXISTING)
//    }
}