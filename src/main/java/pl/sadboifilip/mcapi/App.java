package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("REST API ENABLED!");
        getLogger().info("REST API ENABLED!");

        this.getCommand("apitoken").setExecutor(new ApiCredentialsCommand());
    }
}
