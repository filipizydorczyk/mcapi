package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;
import pl.sadboifilip.mcapi.commands.ApiStartCommand;
import pl.sadboifilip.mcapi.events.PlayerJoinListener;

public class App extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("mcapi-token").setExecutor(new ApiCredentialsCommand());
        this.getCommand("mcapi-start").setExecutor(new ApiStartCommand());

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // getLogger().info("REST API ENABLED!");
    }

    @Override
    public void onDisable() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);
        try {
            context.getBean(Javalin.class).stop();
        } finally {
            context.close();
        }
    }

}
