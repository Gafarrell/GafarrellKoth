package io.github.gafarrell.commands.creation;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothDeleteCmd extends KothCmd {
    private CommandSender sender;
    private String args;

    public KothDeleteCmd(CommandSender sender, String... args) {
        super(sender, args);
        this.args = args[0];
    }

    @Override
    public boolean Execute() {
        successful = KothStorage.remove(args);

        if (!successful){
            errorMessage = "KoTH \"" + args + "\" does not exits.";
        }

        return successful;
    }

    @Override
    public String getResponseMessage() {
        if (!successful) return "Command was not successful!\n Error: " + errorMessage;

        return String.format("Successfully removed KoTH %s!\n", args);
    }
}
