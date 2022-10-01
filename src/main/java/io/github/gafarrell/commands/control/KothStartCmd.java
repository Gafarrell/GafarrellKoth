package io.github.gafarrell.commands.control;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothController;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothStartCmd extends KothCmd {


    public KothStartCmd(CommandSender sender, String[] subargs)
    {
        super(sender, subargs);
    }

    @Override
    public void Execute() {
        if (!successful || args == null || args.length < 1) {
            successful = false;
            return;
        }

        KothController controller = KothStorage.makeController(args[0]);

        if (controller == null) {
            responseMessage = "§cGiven KoTH name does not exist!";
            commandSender.sendMessage(responseMessage);
            successful = false;
            return;
        }

        controller.start();
        successful = true;
    }

    @Override
    public String getResponseMessage() {
        if (successful) return "§aSuccessfully started KoTH: " + args[0];

        return "§cUnable to create KoTH!";
    }
}
