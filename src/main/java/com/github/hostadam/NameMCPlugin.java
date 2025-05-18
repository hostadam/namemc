package com.github.hostadam;

import com.github.hostadam.task.CheckerTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NameMCPlugin extends JavaPlugin {

    @Getter
    private static NameMCPlugin instance;

    @Getter
    private List<UUID> likes = new ArrayList<>();

    public boolean hasLiked(UUID uniqueId) {
        return likes.contains(uniqueId);
    }

    public void giveRewards(UUID uniqueId) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
        if(Bukkit.isPrimaryThread()) {
            this.getConfig().getStringList("rewards").forEach(string -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", offlinePlayer.getName()));
            });
        } else {
            Bukkit.getScheduler().runTask(this, () -> {
                this.getConfig().getStringList("unlike-commands").forEach(string -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", offlinePlayer.getName()));
                });
            });
        }
    }

    public void runUnlike(UUID uniqueId) {
        if(!this.getConfig().contains("unlike-commands")) return;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
        if(Bukkit.isPrimaryThread()) {
            this.getConfig().getStringList("unlike-commands").forEach(string -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", offlinePlayer.getName()));
            });
        } else {
            Bukkit.getScheduler().runTask(this, () -> {
                this.getConfig().getStringList("unlike-commands").forEach(string -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", offlinePlayer.getName()));
                });
            });
        }
    }

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        saveDefaultConfig();
        instance = this;

        for(String string : this.getConfig().getStringList("liked-users")) {
            UUID uniqueId = UUID.fromString(string);
            this.likes.add(uniqueId);
        }

        new CheckerTask(this).runTaskTimerAsynchronously(this, this.getConfig().getInt("check-interval", 12000), this.getConfig().getInt("check-interval", 12000));
    }

    @Override
    public void onDisable() {
        this.getConfig().set("liked-users", this.likes.stream().map(UUID::toString).collect(Collectors.toList()));
        this.saveConfig();
    }
}
