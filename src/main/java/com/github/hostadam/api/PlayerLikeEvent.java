package com.github.hostadam.api;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class PlayerLikeEvent extends Event {

    private UUID uniqueId;
    public PlayerLikeEvent(UUID uniqueId) {
        super();
        this.uniqueId = uniqueId;
    }

    private static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
