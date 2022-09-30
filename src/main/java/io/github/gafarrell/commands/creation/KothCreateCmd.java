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
            responseMessage = "Sender is not a player!";
            successful = false;
            return;
        }

        player = (Player) sender;
    }

    @Override
    public void Execute() {
        newKoth = new KothObject("Koth" + KothStorage.GetKothCount());

        if (args == null) {
            KothStorage.put(newKoth);
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("running")) {
            responseMessage = "§cCannot create a KoTH named \"running\"";
            successful = false;
            return;
        }

        switch (args.length){
            case 1:
                newKoth.setName(args[0]);
                newKoth.setCaptureRegion(new Region(player.getLocation(), 3));
                break;
            case 2:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Long.parseLong(args[1]));
                newKoth.setCaptureRegion(new Region(player.getLocation(), 3));
                break;
            case 3:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Long.parseLong(args[1]));
                newKoth.setTimeToCapture(Long.parseLong(args[2]));
                newKoth.setCaptureRegion(new Region(player.getLocation(), 3));
                break;
            case 4:
                newKoth.setName(args[0]);
                newKoth.setActiveDuration(Long.parseLong(args[1]));
                newKoth.setTimeToCapture(Long.parseLong(args[2]));
                newKoth.setCaptureRegion(new Region(player.getLocation(), Integer.parseInt(args[3])));
                break;
            default:
                successful = false;
                return;
        }

        KothStorage.put(newKoth);
        successful = true;
    }

    @Override
    public String getResponseMessage() {
        if (!successful) return "§cCommand was not successful!\n" + responseMessage;

        return "§aSuccessfully create KoTH!\n" + newKoth.toString();
    }

    public KothObject getNewKoth() {
        return newKoth;
    }
}
