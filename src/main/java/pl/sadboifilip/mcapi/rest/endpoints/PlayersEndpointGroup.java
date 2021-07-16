package pl.sadboifilip.mcapi.rest.endpoints;

import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.DefaultResponse;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.scheduler.BukkitScheduler;

public class PlayersEndpointGroup extends BaseEndpointGroup {

    public PlayersEndpointGroup() {
        super();
    }

    @Override
    public void addEndpoints() {
        get("logged", context -> {
            final List<PlayerResponse> list = this.app.getServer().getOnlinePlayers().stream().map(PlayerResponse::new)
                    .collect(Collectors.toList());
            context.json(list);
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        post(":id/kick", context -> {
            final UUID userId = UUID.fromString(context.pathParam("id"));

            try {
                final BukkitScheduler scheduler = this.app.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(this.app, () -> {
                    app.getServer().getPlayer(userId).kickPlayer("Bye!");
                }, 0L);

            } catch (Exception e) {
                context.json(new DefaultResponse(true, e.getMessage()));
            }

            context.json(new DefaultResponse("Player kicked."));

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
