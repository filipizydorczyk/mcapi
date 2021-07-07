package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;
import pl.sadboifilip.mcapi.rest.RESTApp;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("REST API ENABLED!");
        getLogger().info("REST API ENABLED!");

        RESTApp.getApp().start(7000);

        this.getCommand("apitoken").setExecutor(new ApiCredentialsCommand());
    }
}
