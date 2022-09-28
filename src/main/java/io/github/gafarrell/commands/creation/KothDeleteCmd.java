package io.github.gafarrell.commands.creation;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothDeleteCmd extends KothCmd {
    private CommandSender sender;
    private String args;

    public KothDeleteCmd(CommandSender sender, String args) {
        this.args = args;
    }

    @Override
    public boolean Execute() {
        if (!successful) return (successful=false);

        successful = KothStorage.remove(args);

        if (!successful){
            errorMessage = "KoTH \"" + args + "\" does not exits.";
        }

        return successful;
    }
}
