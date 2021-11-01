package pl.sadboifilip.mcapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.commands.ApiCredentialsCommand;
import pl.sadboifilip.mcapi.events.PlayerJoinListener;

public class App extends JavaPlugin {

    private Javalin app;

    @Override
    public void onEnable() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);
        try {
            this.app = context.getBean(Javalin.class);
        } finally {
            context.close();
        }

        this.getCommand("mcapi-token").setExecutor(new ApiCredentialsCommand());
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        getLogger().info("REST API ENABLED!");
    }

    @Override
    public void onDisable() {
        app.stop();
    }

}
