package pl.sadboifilip.mcapi.rest.endpoints;

import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.DefaultResponse;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

public class WhitelistEndpointGroup extends BaseEndpointGroup {

    public WhitelistEndpointGroup() {
        super();
    }

    @Override
    public void addEndpoints() {
        get("/", context -> {
            final List<PlayerResponse> list = this.app.getServer().getWhitelistedPlayers().stream()
                    .filter(pl -> pl instanceof Player).map(pl -> new PlayerResponse((Player) pl))
                    .collect(Collectors.toList());
            context.json(list);
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        post("/:id", context -> {
            final UUID userId = UUID.fromString(context.pathParam("id"));

            if (this.app.getServer().hasWhitelist()) {
                this.app.getServer().getPlayer(userId).setWhitelisted(true);
                context.json(new DefaultResponse("User sucessfully added to whitelist"));
            } else {
                context.json(new DefaultResponse(true, "Whitelist is not enabled on the server"));
            }

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
