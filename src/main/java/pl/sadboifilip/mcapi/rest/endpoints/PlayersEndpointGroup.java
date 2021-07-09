package pl.sadboifilip.mcapi.rest.endpoints;

import io.javalin.apibuilder.EndpointGroup;
import pl.sadboifilip.mcapi.App;
import pl.sadboifilip.mcapi.ApplicationConfig;
import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.scheduler.BukkitScheduler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PlayersEndpointGroup implements EndpointGroup {

    private App app;

    public PlayersEndpointGroup() {
        super();
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);

        try {
            this.app = context.getBean(App.class);
        } finally {
            context.close();
        }
    }

    @Override
    public void addEndpoints() {
        get("logged", context -> {
            final List<PlayerResponse> list = this.app.getServer().getOnlinePlayers().stream().map(PlayerResponse::new)
                    .collect(Collectors.toList());
            context.json(list);
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        get(":id/kick", context -> {
            System.out.println(context.pathParam("id"));
            final UUID userId = UUID.fromString(context.pathParam("id"));

            try {
                final BukkitScheduler scheduler = this.app.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(this.app, new Runnable() {
                    @Override
                    public void run() {
                        app.getServer().getPlayer(userId).kickPlayer("Bye!");
                    }
                }, 0L);

            } catch (Exception e) {
                context.result(e.getMessage());
            }

            context.result("true");

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
