package io.github.gafarrell.commands.info;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothInfoCmd extends KothCmd {
    public KothInfoCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);
    }

    @Override
    public void Execute() {
        StringBuilder info = new StringBuilder();

        if (args != null && args.length > 0){
            if (args[0].equalsIgnoreCase("running")) {
                commandSender.sendMessage("Appending running koths.");
                commandSender.sendMessage(KothStorage.getActiveKothList());
            }
            else {
                commandSender.sendMessage("Appending single koth.");
                KothObject koth = KothStorage.getKothByName(args[0]);
                commandSender.sendMessage(koth == null ? "§cKoTH with ID " + args[0] + " was not found." : koth.toString());
            }
        }
        else {
            commandSender.sendMessage(info.append(KothStorage.getKothNames()).toString());
        }

    }

    @Override
    public String getResponseMessage() {
        return null;
    }
}
