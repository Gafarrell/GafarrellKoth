package io.github.gafarrell.commands.info;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothInfoCmd extends KothCmd {
    public KothInfoCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);
    }

    @Override
    public boolean Execute() {
        StringBuilder info = new StringBuilder();
        if (args != null && args.length > 0){
            info.append(KothStorage.getKothByName(args[0]).toString());
        }
        else
            KothStorage.getKothNames();

        commandSender.sendMessage(info.toString());
        return false;
    }

    @Override
    public String getSuccessMessage() {
        return null;
    }
}
