package pl.sadboifilip.mcapi.rest.endpoints;

import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.DefaultResponse;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WhitelistEndpointGroup extends BaseEndpointGroup {

    public WhitelistEndpointGroup() {
        super();
    }

    @Override
    public void addEndpoints() {
        get("/", context -> {
            if (this.app.getServer().hasWhitelist()) {
                final List<PlayerResponse> list = this.app.getServer().getWhitelistedPlayers().stream()
                        .map(pl -> new PlayerResponse(pl)).collect(Collectors.toList());

                context.json(list);
            } else {
                context.json(new ArrayList<>());
            }

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        get("/status", context -> {
            if (this.app.getServer().hasWhitelist()) {
                context.json(new DefaultResponse("Whitelist is up."));
            } else {
                context.json(new DefaultResponse("Whitelist is down."));
            }
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        post("/status", context -> {
            this.app.getServer().setWhitelist(true);

            context.json(new DefaultResponse("Whitelist is now enabled."));
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        delete("/status", context -> {
            this.app.getServer().setWhitelist(false);

            context.json(new DefaultResponse("Whitelist is now disabled."));
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        post("/:id", context -> {
            final UUID userId = UUID.fromString(context.pathParam("id"));

            if (this.app.getServer().hasWhitelist()) {
                this.app.getServer().getOfflinePlayer(userId).setWhitelisted(true);
                context.json(new DefaultResponse("User sucessfully added to whitelist"));
            } else {
                context.json(new DefaultResponse(true, "Whitelist is not enabled on the server"));
            }

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        delete("/:id", context -> {
            final UUID userId = UUID.fromString(context.pathParam("id"));

            if (this.app.getServer().hasWhitelist()) {
                this.app.getServer().getOfflinePlayer(userId).setWhitelisted(false);
                context.json(new DefaultResponse("User sucessfully removed from whitelist"));
            } else {
                context.json(new DefaultResponse(true, "Whitelist is not enabled on the server"));
            }

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
