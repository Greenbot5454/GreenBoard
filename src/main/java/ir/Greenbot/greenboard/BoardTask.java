package ir.Greenbot.greenboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardTask extends BukkitRunnable {

    private final GreenBoard plugin;
    private final BoardManager boardManager;

    public BoardTask(GreenBoard plugin, BoardManager boardManager) {
        this.plugin = plugin;
        this.boardManager = boardManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            boardManager.updateBoard(player);
        }
    }
}
