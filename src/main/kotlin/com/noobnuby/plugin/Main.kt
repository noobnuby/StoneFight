package com.noobnuby.plugin

import com.noobnuby.plugin.commands.StoneFight
import com.noobnuby.plugin.commands.Test
import com.noobnuby.plugin.events.*
import com.noobnuby.plugin.handlers.Scheduler
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.jar.JarFile
import kotlin.Comparator
import kotlin.collections.ArrayList

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
            Test.register(this)
        }

        WorldCreator("world").createWorld()

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
        worldLoad()
    }

    fun worldLoad() {
        val worldResourcePath = "world"

        if (Files.exists(dataFolder.toPath().resolve(worldResourcePath))) {
            return
        }

        val jarFile = File(javaClass.protectionDomain.codeSource.location.toURI().path)
        val jar = JarFile(jarFile)

        val entries = jar.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.name.startsWith(worldResourcePath)) {
                val file = File(dataFolder, entry.name)

                if (entry.isDirectory) {
                    file.mkdir()
                } else {
                    file.parentFile.mkdirs()

                    // get the input stream
                    jar.getInputStream(entry).use { input ->
                        BufferedInputStream(input).use { bufferedInput ->
                            FileOutputStream(file).use { output ->
                                BufferedOutputStream(output).use { bufferedOutput ->
                                    val buffer = ByteArray(1024)
                                    var length: Int
                                    while (bufferedInput.read(buffer).also { length = it } != -1) {
                                        bufferedOutput.write(buffer, 0, length)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDisable() {
        for (onlinePlayer in Bukkit.getOnlinePlayers()) { onlinePlayer.kick() }
        val worldFolderPath = server.worldContainer.resolve("world").toPath()
        val copiedWorldPath = dataFolder.toPath().resolve("world")

        val world = Bukkit.getWorld("world")!!
        Bukkit.unloadWorld(world, false)
        deleteDirectory(worldFolderPath)
        copyWorld(copiedWorldPath.toFile(), worldFolderPath.toFile())
    }

    private fun deleteDirectory(directory: Path) {
        Files.walk(directory)
            .sorted(Comparator.reverseOrder())
            .map { it.toFile() }
            .forEach { it.delete() }
    }

    private fun copyWorld(source: File, target: File) {
        try {
            val ignore: ArrayList<String> = ArrayList(Arrays.asList("uid.dat", "session.dat"))
            if (!ignore.contains(source.name)) {
                if (source.isDirectory) {
                    if (!target.exists()) target.mkdirs()
                    val files = source.list()
                    for (file in files!!) {
                        val srcFile = File(source, file)
                        val destFile = File(target, file)
                        copyWorld(srcFile, destFile)
                    }
                } else {
                    val `in`: InputStream = FileInputStream(source)
                    val out: OutputStream = FileOutputStream(target)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (`in`.read(buffer).also { length = it } > 0) out.write(buffer, 0, length)
                    `in`.close()
                    out.close()
                }
            }
        } catch (_: IOException) {
        }
    }
}