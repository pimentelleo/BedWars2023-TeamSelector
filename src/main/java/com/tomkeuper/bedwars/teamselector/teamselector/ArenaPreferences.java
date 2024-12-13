package com.tomkeuper.bedwars.teamselector.teamselector;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.teamselector.api.events.TeamSelectorAbortEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class ArenaPreferences {

    private final IArena arena;

    private final HashMap<Player, ITeam> selections = new HashMap<>();

    protected ArenaPreferences(IArena arena) {
        this.arena = arena;
    }

    public void removePlayer(Player player) {
        // todo refresh guis
        if (selections.remove(player) != null) {
            Bukkit.getPluginManager().callEvent(new TeamSelectorAbortEvent(player));
        }
    }

    public ITeam getTeam(Player player) {
        return selections.get(player);
    }

    public int getTeamsCount() {
        return (int) selections.values().stream().distinct().count();
    }

    public int getMembersCount() {
        return selections.size();
    }

    int getPlayersCount(ITeam team) {
        return (int) selections.values().stream().filter(team2 -> team2.equals(team)).count();
    }

    public List<Player> getMembers(ITeam team) {
        ArrayList<Player> members = new ArrayList<>();
        if (selections.isEmpty()) return members;
        selections.entrySet().stream().filter((entry) -> entry.getValue().equals(team)).forEach(entry -> members.add(entry.getKey()));
        return members;
    }

    public void setPlayerTeam(Player player, ITeam team) {
        selections.remove(player);
        selections.put(player, team);
    }
}
