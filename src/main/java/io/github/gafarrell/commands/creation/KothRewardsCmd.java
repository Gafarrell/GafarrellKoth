package io.github.gafarrell.commands.creation;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class KothRewardsCmd extends KothCmd implements Listener {
    private final Inventory rewardsChest = Bukkit.createInventory(null, InventoryType.CHEST, "KoTH Rewards");
    private KothObject kothToEdit;

    public KothRewardsCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);

        kothToEdit = KothStorage.getKothByName(args[0]);

        if (kothToEdit == null) {
            successful = false;
            return;
        }

        if (kothToEdit.getRewards() == null) return;
        for (ItemStack item : kothToEdit.getRewards())
            if (item != null) rewardsChest.addItem(item);

    }

    @Override
    public boolean Execute() {
        if (args == null || args.length == 0 || !successful){
            errorMessage = "Invalid arguments";
            return (successful = false);
        }
        if (!(commandSender instanceof HumanEntity entity)) {
            errorMessage = "Command sender must be a player!";
            return (successful = false);
        }

        entity.openInventory(rewardsChest);
        Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getServer().getPluginManager().getPlugin("GafarrellKoTH"));

        return true;
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent e){
        if (!(e.getInventory().equals(rewardsChest) || !successful) || !successful) {
            errorMessage = "Could not find koth " + args[0];
            return;
        }

        e.getPlayer().sendMessage("Successfully set rewards for koth: ");
        ArrayList<ItemStack> validItems = new ArrayList<>();
        for (ItemStack s : e.getInventory().getContents()){
            if (s != null) validItems.add(s);
        }
        kothToEdit.setRewards(validItems.toArray(new ItemStack[0]));
    }

    @Override
    public String getResponseMessage() {
        return errorMessage;
    }
}
