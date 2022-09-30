package io.github.gafarrell.commands.control;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothController;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.CommandSender;

public class KothStartCmd extends KothCmd {

    public KothStartCmd(CommandSender sender, String[] subargs)
    {
        super(sender, subargs);
    }

    @Override
    public boolean Execute() {
        if (!successful || args == null || args.length < 1) return (successful = false);

        KothController controller = KothStorage.makeController(args[0]);

        if (controller == null) {
            errorMessage = "Given KoTH name does not exist!";
            commandSender.sendMessage(errorMessage);
            return (successful = false);
        }

        controller.start();
        return (successful = true);
    }

    @Override
    public String getResponseMessage() {
        if (successful) return "Successfully started KoTH: " + args[0];

        return "Unable to create KoTH!";
    }
}
