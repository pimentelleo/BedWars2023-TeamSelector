package com.tomkeuper.bedwars.teamselector.api;

import org.bukkit.entity.Player;

import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.teamselector.Main;

public class TeamSelector implements TeamSelectorAPI {

    @Override
    public ITeam getSelectedTeam(Player player) {
        IArena a = Main.bw.getArenaUtil().getArenaByPlayer(player);
        return a == null ? null : a.getTeam(player);
    }

    @Override
    public int getApiVersion() {
        return 2;
    }
}
