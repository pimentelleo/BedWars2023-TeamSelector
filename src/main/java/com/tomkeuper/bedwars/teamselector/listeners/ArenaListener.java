package com.tomkeuper.bedwars.teamselector.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.tomkeuper.bedwars.api.events.server.ArenaEnableEvent;
import com.tomkeuper.bedwars.teamselector.Main;
import com.tomkeuper.bedwars.teamselector.teamselector.ArenaPreferences;
import com.tomkeuper.bedwars.teamselector.teamselector.TeamManager;
import com.tomkeuper.bedwars.teamselector.teamselector.TeamSelectorAssigner;
import com.tomkeuper.bedwars.teamselector.teamselector.TeamSelectorGUI;

public class ArenaListener implements Listener {

    @EventHandler
    public void onBwArenaJoin(@NotNull PlayerJoinArenaEvent e) {
        if (e.isCancelled()) return;
        if (e.isSpectator()) return;
        if (e.getArena() == null) return;
        if (e.getArena().getStatus() == GameState.waiting || e.getArena().getStatus() == GameState.starting) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                if (e.getArena().isPlayer(e.getPlayer()) || e.getArena().getStatus() != GameState.playing) {
                    TeamSelectorGUI.giveItem(e.getPlayer(), null);
                }
            }, 30L);
        }
    }

    @EventHandler
    //Remove player from team
    public void onBwArenaLeave(@NotNull PlayerLeaveArenaEvent e) {
        IArena a = e.getArena();
        if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
            TeamManager.getInstance().onQuit(a, e.getPlayer());
        }
    }

    @EventHandler
    public void onStatusChange(@NotNull GameStateChangeEvent e) {
        if (e.getNewState() == GameState.starting) {

            ArenaPreferences pref = TeamManager.getInstance().getArena(e.getArena());
            if (pref == null) return;

            // do not start with a single team
            int size = e.getArena().getPlayers().size();
            int teams = pref.getTeamsCount();
            int members = pref.getMembersCount();
            if (size - members <= 0 && teams == 1) {
                e.getArena().setStatus(GameState.waiting);
            }
        }
        if (e.getNewState() == GameState.playing || e.getNewState() == GameState.restarting) {
            TeamManager.getInstance().clearArenaCache(e.getArena());
        }
    }

    @EventHandler
    public void onArenaLoad(@NotNull ArenaEnableEvent event) {
        event.getArena().setTeamAssigner(new TeamSelectorAssigner());
    }
}
