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
            if (args[0].equalsIgnoreCase("running"))
                info.append(KothStorage.getActiveKothList());
            else {
                KothObject koth = KothStorage.getKothByName(args[0]);
                info.append(koth == null ? "§cKoTH with ID " + args[0] + " was not found." : koth.toString());
            }
        }
        else
            info.append(KothStorage.getKothNames());

        responseMessage = info.toString();
    }

    @Override
    public String getResponseMessage() {
        return null;
    }
}
