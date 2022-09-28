package io.github.gafarrell.commands;

import io.github.gafarrell.commands.creation.KothCreateCmd;
import io.github.gafarrell.commands.creation.KothDeleteCmd;
import io.github.gafarrell.commands.info.KothInfoCmd;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class KothCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("koth"))
        {
            if (args.length < 1) {
                commandSender.sendMessage("Welcome to KoTH, I hope you enjoy! Please use /koth help for a list of commands.");
                return true;
            }

            KothCmd kothCommand;
            String[] subArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;

            switch (args[0].toLowerCase())
            {
                case "create":
                    kothCommand = new KothCreateCmd(commandSender, subArgs);

                case "delete":
                    if (args.length < 2) return false;
                    kothCommand = new KothDeleteCmd(commandSender, args[1]);

                case "info":
                case "list":
                    kothCommand = new KothInfoCmd(commandSender, subArgs);
                    break;

                case "help":
                case "start":
                case "pause":
                    commandSender.sendMessage("Command not yet implemented!");
                    break;
                default:
                    commandSender.sendMessage("That's not a KoTH command! Please try again. =]");
            }
        }
        return true;
    }
}
