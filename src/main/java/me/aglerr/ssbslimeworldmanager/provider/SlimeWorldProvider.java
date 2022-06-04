package me.aglerr.ssbslimeworldmanager.provider;

import com.bgsoftware.superiorskyblock.api.hooks.WorldsProvider;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.grinderwolf.swm.api.world.SlimeWorld;
import me.aglerr.ssbslimeworldmanager.ConfigValue;
import me.aglerr.ssbslimeworldmanager.tasks.TaskManager;
import me.aglerr.ssbslimeworldmanager.utils.SlimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class SlimeWorldProvider implements WorldsProvider {

    private final SlimeUtils slimeUtils;
    private final TaskManager taskManager;
    public SlimeWorldProvider(SlimeUtils slimeUtils, TaskManager taskManager){
        this.slimeUtils = slimeUtils;
        this.taskManager = taskManager;
    }

    @Override
    public void prepareWorlds() {

    }

    @Override
    public World getIslandsWorld(Island island, World.Environment environment) {
        SlimeWorld slimeWorld = slimeUtils.loadAndGetWorld(island, environment);
        taskManager.putAndGetTask(slimeWorld.getName()).updateLastTime();
        return Bukkit.getWorld(slimeWorld.getName());
    }

    @Override
    public boolean isIslandsWorld(World world) {
        return this.slimeUtils.isIslandsWorld(world.getName());
    }

    @Override
    public Location getNextLocation(Location previousLocation, int islandsHeight, int maxIslandSize, UUID islandOwner, UUID islandUUID) {
        SlimeWorld slimeWorld = slimeUtils.loadAndGetWorld(islandUUID, World.Environment.NORMAL);
        this.taskManager.putAndGetTask(slimeWorld.getName());
        return new Location(Bukkit.getWorld(slimeWorld.getName()), 0, islandsHeight, 0);
    }

    @Override
    public void finishIslandCreation(Location islandsLocation, UUID islandOwner, UUID islandUUID) {

    }

    @Override
    public void prepareTeleport(Island island, Location location, Runnable finishCallback) {
        if(!island.isSpawn()) {
            SlimeWorld slimeWorld = slimeUtils.loadAndGetWorld(island, location.getWorld().getEnvironment());
            this.taskManager.putAndGetTask(slimeWorld.getName()).updateLastTime();
        }
        finishCallback.run();
    }

    public boolean isNormalEnabled(){
        return ConfigValue.NORMAL_ENABLED;
    }

    public boolean isNormalUnlocked(){
        return ConfigValue.NORMAL_UNLOCKED;
    }

    @Override
    public boolean isNetherEnabled() {
        return ConfigValue.NETHER_ENABLED;
    }

    @Override
    public boolean isNetherUnlocked() {
        return ConfigValue.NETHER_UNLOCKED;
    }

    @Override
    public boolean isEndEnabled() {
        return ConfigValue.END_ENABLED;
    }

    @Override
    public boolean isEndUnlocked() {
        return ConfigValue.END_UNLOCKED;
    }
}
