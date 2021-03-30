package monplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		this . getCommand ( "kit" ) . setExecutor ( new KitCommand () ) ;
		System . out . println ( " Mon plugin s ’ est bien lance ! " ) ;
	}
	@Override
	public void onDisable() {
		System . out . println ( " Mon plugin s ’ est bien stoppe ! " ) ;
	}
}