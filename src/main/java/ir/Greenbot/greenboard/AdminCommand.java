package ir.Greenbot.greenboard;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCommand implements CommandExecutor {

    private final GreenBoard plugin;
    private final BoardManager boardManager;

    public AdminCommand(GreenBoard plugin, BoardManager boardManager) {
        this.plugin = plugin;
        this.boardManager = boardManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenboard.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission!");
            return true;
        }

        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /greenboard reload");
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "[GreenBoard] Config reloaded successfully!");
        return true;
    }
}
