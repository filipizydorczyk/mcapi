package pl.sadboifilip.mcapi.commands;

import io.javalin.Javalin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.sadboifilip.mcapi.ApplicationConfig;

public class ApiStopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }

        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        try {
            context.getBean(Javalin.class).stop();
        } finally {
            context.close();
        }

        return true;
    }

}
