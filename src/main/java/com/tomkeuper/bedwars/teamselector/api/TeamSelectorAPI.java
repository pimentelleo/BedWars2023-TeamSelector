package com.tomkeuper.bedwars.teamselector.api;

import org.bukkit.entity.Player;

import com.tomkeuper.bedwars.api.arena.team.ITeam;

@SuppressWarnings("unused")
public interface TeamSelectorAPI {

    /**
     * Get player's selected team
     */
    ITeam getSelectedTeam(Player player);


    /**
     * Get api version
     */
    int getApiVersion();
}
