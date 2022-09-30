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
    public void Execute() {
        successful = KothStorage.remove(args);

        if (!successful){
            responseMessage = "§cKoTH \"" + args + "\" does not exits.";
        }

    }

    @Override
    public String getResponseMessage() {
        if (!successful) return "§cCommand was not successful!\n Error: " + responseMessage;

        return String.format("§aSuccessfully removed KoTH %s!\n", args);
    }
}
