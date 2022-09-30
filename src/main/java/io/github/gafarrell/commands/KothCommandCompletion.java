package io.github.gafarrell.commands;

import io.github.gafarrell.koth.KothController;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class KothCommandCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("koth")) return new ArrayList<>();


        if (args.length == 0)
            return KothCommandHandler.tabCompletes;

        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            switch (args[0]) {
                case "create":
                case "remove":
                case "delete":
                case "rewards":
                case "edit":
                case "info":
                case "start":
                    tabComplete.addAll(Arrays.asList(KothStorage.getCurrentKoths().keySet().toArray(new String[0])));
                    break;
                case "pause":
                    ArrayList<KothController> activeControllers = KothStorage.getCurrentControllers();
                    for (KothController controller : activeControllers)
                        tabComplete.add(controller.getName());
                    break;
                default:
                    return tabComplete;
            }
        }

//        if (args.length == 2){
//            switch (args[0]){
//
//            }
//        }

        return tabComplete;
    }
}
