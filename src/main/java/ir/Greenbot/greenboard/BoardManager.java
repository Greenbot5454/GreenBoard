package ir.Greenbot.greenboard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class BoardManager {

    private final GreenBoard plugin;
    private final ChatColor[] colors = {
            ChatColor.BLACK, ChatColor.DARK_BLUE, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA,
            ChatColor.DARK_RED, ChatColor.DARK_PURPLE, ChatColor.GOLD, ChatColor.GRAY,
            ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.GREEN, ChatColor.AQUA,
            ChatColor.RED, ChatColor.LIGHT_PURPLE, ChatColor.YELLOW
    };

    public BoardManager(GreenBoard plugin) {
        this.plugin = plugin;
    }

    public void updateBoard(Player player) {
        List<String> enabledWorlds = plugin.getConfig().getStringList("enabled-worlds");
        
        if (!enabledWorlds.contains(player.getWorld().getName())) {
            if (player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
            return;
        }

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title", "&a&lSKYWARS"));
        
        Objective obj = board.registerNewObjective("greenboard", "dummy");
        obj.setDisplayName(title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = plugin.getConfig().getStringList("lines");
        int score = lines.size();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }
            
            line = ChatColor.translateAlternateColorCodes('&', line);

            String entry = colors[i % colors.length].toString();
            Team team = board.registerNewTeam("line_" + i);
            team.addEntry(entry);
            
            if (line.length() > 16) {
                team.setPrefix(line.substring(0, 16));
                String suffix = line.substring(16);
                if (suffix.length() > 16) {
                    suffix = suffix.substring(0, 16);
                }
                team.setSuffix(suffix);
            } else {
                team.setPrefix(line);
            }
            
            obj.getScore(entry).setScore(score--);
        }

        player.setScoreboard(board);
    }
}
