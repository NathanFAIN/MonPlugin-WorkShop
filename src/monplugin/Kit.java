package monplugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Kit {
	private static Map<String, Kit> kits = new HashMap<String, Kit>();
    private static String path = "plugins/Kits/";

    private String name;
    private ArrayList<ItemStack> contents = new ArrayList<ItemStack>();

    static {
        File dir = new File(path);
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {
			for (File child : directoryListing) {
				String fileName = child.getName();
				String kitName = fileName.substring(0, fileName.length() - 4);
				Kit kit = new Kit(kitName);
				Kit.kits.put(kitName, kit);
			}
		}
    }

    public static Kit getKit(String kitName) {
        return kits.get(kitName);
    }
    
    public static Kit createKit(String kitName, Inventory playerInventory) {
    	Kit kit = new Kit(kitName, playerInventory);
    	kits.put(kitName, kit);
        return kit;
    }

    public Kit(String kitName) {
        this.name = kitName;
    
        File file = new File(path + this.name + ".yml");
		if (file.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("Items");
			for (Integer i = 0; i < 41; i++) {
                ItemStack item = configurationSection.getItemStack(i.toString());
				if (item == null) {
					item = new ItemStack(Material.AIR);
				}
                this.contents.add(item);
			}
            kits.put(this.name, this);
        }
    }

    public Kit(String kitName, Inventory playerInventory) {
        this.name = kitName;
        File file = new File(path + this.name + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        for (int i = 0; i < playerInventory.getSize(); i++) {
        	ItemStack item = playerInventory.getItem(i);
			if (item == null) {
				item = new ItemStack(Material.AIR);
			}
			yamlConfiguration.set("Items." + i, item);
            this.contents.add(item);
        }
        try {
			yamlConfiguration.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void delete() {
		File file = new File(path + this.name + ".yml");

		if (file.exists()) {
			file.delete();
		}
        kits.remove(this.name);
	}

    public void giveContents(Player player) {
        player.getInventory().clear();
        for (int i = 0; i < 41; i++) {
			player.getInventory().setItem(i, this.contents.get(i));
		}
        player.updateInventory();
    }
}