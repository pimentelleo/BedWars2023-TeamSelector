package com.tomkeuper.bedwars.teamselector.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import com.tomkeuper.bedwars.teamselector.api.events.TeamSelectorAbortEvent;
import com.tomkeuper.bedwars.teamselector.api.events.TeamSelectorChooseEvent;
import com.tomkeuper.bedwars.teamselector.teamselector.TeamSelectorGUI;

public class SelectorGuiUpdateListener implements Listener {

    @EventHandler
    public void onTeamJoin(@NotNull TeamSelectorChooseEvent e) {
        if (e.isCancelled()) return;
        TeamSelectorGUI.updateGUIs();
    }

    @EventHandler
    public void onAbort(TeamSelectorAbortEvent e) {
        TeamSelectorGUI.updateGUIs();
    }
}
