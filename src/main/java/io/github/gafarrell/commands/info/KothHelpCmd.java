package io.github.gafarrell.commands.info;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.commands.KothCommandHandler;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class KothHelpCmd extends KothCmd {
    public KothHelpCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);
    }

    @Override
    public void Execute() {
        commandSender.sendMessage("§a");
            for (String s : KothCommandHandler.tabCompletes){
                commandSender.sendMessage("");
            }
    }
}
