package tranlong5252.exportlore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommandMain implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) return true;
		if (!player.hasPermission("exportlore.use")) return true;
		if (args.length == 0) {
			player.sendMessage("§cUsage: /exportlore <filename>");
			return true;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item.getType().isAir()) {
			player.sendMessage("§cYou must hold an item in your hand!");
			return true;
		}
		ItemMeta meta = item.getItemMeta();
		if (meta == null || !meta.hasLore()) {
			player.sendMessage("§cThis item has no lore!");
			return true;
		}
		List<String> lore = meta.getLore();
		if (lore == null || lore.isEmpty()) {
			player.sendMessage("§cThis item has no lore!");
			return true;
		}
		lore.replaceAll(s -> s.replace("§", "&"));
		File file = new File(ExportLore.getInstance().getDataFolder(), args[0] + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		yaml.set("display_name", meta.getDisplayName().replace("§", "&"));
		yaml.set("material", item.getType().name());
		yaml.set("amount", item.getAmount());
		if (meta.hasCustomModelData()) yaml.set("model_data", meta.getCustomModelData());
		yaml.set("lore", lore);
		try {
			yaml.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		player.sendMessage("§aExported lore to " + file.getName());
		return true;
	}
}
