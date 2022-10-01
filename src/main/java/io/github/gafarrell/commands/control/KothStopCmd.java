package io.github.gafarrell.commands.control;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothStopCmd extends KothCmd {
    public KothStopCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);
    }

    @Override
    public void Execute() {
        if (KothStorage.isKothActive(args[0])) {
            KothStorage.pauseKoth(args[0]);
            responseMessage = "§aKoTH '" + args[0] + "' with ID: " + args[1] + " successfully paused.";
        }
        else {
            responseMessage = "§cUnable to pause KoTH because it is not currently active!";
        }
    }
}
