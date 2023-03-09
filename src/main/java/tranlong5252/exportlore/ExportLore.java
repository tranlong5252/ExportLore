package tranlong5252.exportlore;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ExportLore extends JavaPlugin {
	private static ExportLore instance;

	@Override
	public void onEnable() {
		instance = this;
		File folder = getDataFolder(); // Get plugin folder (under plugins folder)
		if (!folder.exists()) folder.mkdir(); // Create folder if not exist
		getCommand("exportlore").setExecutor(new CommandMain()); //register command
	}

	public static ExportLore getInstance() {
		return instance;
	}
}
