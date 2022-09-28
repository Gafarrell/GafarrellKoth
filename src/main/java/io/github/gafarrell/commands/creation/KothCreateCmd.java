package io.github.gafarrell.commands.creation;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import io.github.gafarrell.koth.resources.Region;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class KothCreateCmd extends KothCmd {

    private KothObject newKoth;

    private Player player;

    public KothCreateCmd(CommandSender sender, String[] args) {
        super(sender, args);
        if (!(sender instanceof Player)) {
            errorMessage = "Sender is not a player!";
            successful = false;
            return;
        }

        player = (Player) sender;
    }

    @Override
    public boolean Execute() {
        newKoth = new KothObject("Koth" + KothStorage.GetKothCount());

        if (args == null) {
            KothStorage.put(newKoth);
            return true;
        }

        switch (args.length){
            case 1:
                newKoth.setName(args[0]);
                break;
            case 2:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Float.parseFloat(args[1]));
                break;
            case 3:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Float.parseFloat(args[1]));
                newKoth.setTimeToCapture(Float.parseFloat(args[2]));
                break;
            case 4:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Float.parseFloat(args[1]));
                newKoth.setTimeToCapture(Float.parseFloat(args[2]));
                newKoth.addCaptureRegion(new Region(player.getLocation(), Integer.parseInt(args[3])));
                break;
            default:
                return (successful = false);
        }

        KothStorage.put(newKoth);
        return (successful = true);
    }

    @Override
    public String getSuccessMessage() {
        if (!successful) return "Command was not successful!";

        return "";
    }

    public KothObject getNewKoth() {
        return newKoth;
    }
}
