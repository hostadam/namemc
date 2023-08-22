package com.github.hostadam.task;

import com.github.hostadam.NameMCPlugin;
import com.github.hostadam.api.PlayerLikeEvent;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CheckerTask extends BukkitRunnable {

    private final NameMCPlugin plugin;

    @Override
    public void run() {
        List<UUID> allUuids = new ArrayList<>();

        try {
            URL url = new URL("https://api.namemc.com/server/" + this.plugin.getConfig().getString("server-ip") + "/likes");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

                for(JsonElement element : array) {
                    UUID uniqueId = UUID.fromString(element.getAsString());
                    allUuids.add(uniqueId);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        plugin.getLikes().removeIf(uuid -> !allUuids.contains(uuid));
        allUuids.stream().filter(uuid -> !plugin.hasLiked(uuid)).forEach(uuid -> {
            plugin.getLikes().add(uuid);
            plugin.giveRewards(uuid);
            PlayerLikeEvent likeEvent = new PlayerLikeEvent(uuid);
            Bukkit.getPluginManager().callEvent(likeEvent);
        });
    }
}
