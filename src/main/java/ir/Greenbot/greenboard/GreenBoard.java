package ir.Greenbot.greenboard;

import org.bukkit.plugin.java.JavaPlugin;

public class GreenBoard extends JavaPlugin {

    private BoardManager boardManager;

    @Override
    public void onEnable() {
        getLogger().info("[GreenBoard] Enabling !");
        
        saveDefaultConfig();
        
        this.boardManager = new BoardManager(this);
        
        int interval = getConfig().getInt("update-interval", 20);
        new BoardTask(this, boardManager).runTaskTimer(this, 0L, interval);
        
        getCommand("greenboard").setExecutor(new AdminCommand(this, boardManager));
        
        getLogger().info("[GreenBoard] Enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("[GreenBoard] Disabled.");
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }
}
