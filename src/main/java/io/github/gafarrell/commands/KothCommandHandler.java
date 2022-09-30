package io.github.gafarrell.commands;

import io.github.gafarrell.commands.control.KothStartCmd;
import io.github.gafarrell.commands.creation.KothCreateCmd;
import io.github.gafarrell.commands.creation.KothDeleteCmd;
import io.github.gafarrell.commands.creation.KothEditCmd;
import io.github.gafarrell.commands.creation.KothRewardsCmd;
import io.github.gafarrell.commands.info.KothInfoCmd;
import io.github.gafarrell.koth.KothStorage;
import io.github.gafarrell.koth.KothTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class KothCommandHandler implements CommandExecutor {
    public static final List<String> tabCompletes = new ArrayList<>(Arrays.asList("create", "remove", "info", "start", "edit", "rewards", "pause"));
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("koth"))
        {
            if (args.length < 1) {
                commandSender.sendMessage("Welcome to KoTH, I hope you enjoy! Please use /koth help for a list of commands.");
                return true;
            }

            KothCmd kothCommand = null;
            String[] subArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;

            switch (args[0].toLowerCase()) {
                case "create" -> kothCommand = new KothCreateCmd(commandSender, subArgs);

                case "remove", "delete" -> {
                    if (args.length < 2) return false;
                    kothCommand = new KothDeleteCmd(commandSender, args[1]);
                }

                case "info", "list" -> kothCommand = new KothInfoCmd(commandSender, subArgs);

                case "start" -> kothCommand = new KothStartCmd(commandSender, subArgs);

                case "edit" -> kothCommand = new KothEditCmd(commandSender, subArgs);

                case "rewards" -> kothCommand = new KothRewardsCmd(commandSender, subArgs);

                case "help", "pause" -> commandSender.sendMessage("Command not yet implemented!");

                default -> commandSender.sendMessage("That's not a KoTH command! Please try again. =]");
            }

            if (kothCommand != null) {
                kothCommand.Execute();
                commandSender.sendMessage(kothCommand.getResponseMessage());
            }
        }
        return true;
    }
}
