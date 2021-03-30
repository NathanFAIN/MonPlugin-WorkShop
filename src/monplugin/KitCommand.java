package monplugin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KitCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
	    if (sender instanceof Player) {
	        Player player = (Player)sender;
	        if (args.length == 1)  {
	            String kitName = args[0];
	            Kit kit = Kit.getKit(kitName);
	            if (kit != null) {
	            	kit.giveContents(player);
	            }
	        } else if (args.length == 2) {
	            if (args[0].equals("remove")) {
	                String kitName = args[1];
	                Kit kit = Kit.getKit(kitName);
	                if (kit != null) {
	                	kit.delete();
	                }
	            } else if (args[0].equals("create")) {
	                String kitName = args[1];
	                Inventory playerInventory = player.getInventory();
	                new Kit(kitName, playerInventory);
	            }
	        } else {
	            player.sendMessage(ChatColor.RED + "Usage :");
	            player.sendMessage(ChatColor.RED + "/kit <name>");
	            player.sendMessage(ChatColor.RED + "/kit delete <name>");
	            player.sendMessage(ChatColor.RED + "/kit create <name>");
	        }
	    }
	    return true;
	}
}