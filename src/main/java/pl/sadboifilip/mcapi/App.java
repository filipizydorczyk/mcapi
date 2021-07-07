package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;
import pl.sadboifilip.mcapi.rest.RESTApp;

public class App extends JavaPlugin {

    private Javalin app = null;
    private static App instance = null;

    public App() {
        super();
        if (App.instance == null) {
            App.instance = this;
        }
    }

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

    public static App getInstance() {
        return App.instance;
    }
}
