package pl.sadboifilip.mcapi.commands;

import io.javalin.Javalin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.sadboifilip.mcapi.ApplicationConfig;

public class ApiStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int port = 7000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        if (!sender.isOp()) {
            return false;
        }

        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        try {
            context.getBean(Javalin.class).start(port);
        } finally {
            context.close();
        }
        return true;
    }

}
