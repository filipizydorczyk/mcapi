package pl.sadboifilip.mcapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.ApplicationConfig;

public class ApiStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // final String port = args[0] != null ? args[0] : "7000";

        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);
        try {
            context.getBean(Javalin.class).start(7000);
        } finally {
            context.close();
        }
        return false;
    }

}
