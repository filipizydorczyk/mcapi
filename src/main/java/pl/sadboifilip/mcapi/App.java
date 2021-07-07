package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;
import pl.sadboifilip.mcapi.rest.RESTApp;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin {

    private Javalin app = null;

    @Override
    public void onEnable() {
        getLogger().info("REST API ENABLED!");

        this.app = RESTApp.getApp();

        this.getCommand("apitoken").setExecutor(new ApiCredentialsCommand());
    }

    @Override
    public void onDisable() {
        app.stop();
    }
}
