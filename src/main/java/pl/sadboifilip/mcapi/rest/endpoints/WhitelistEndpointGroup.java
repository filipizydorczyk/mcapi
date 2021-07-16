package pl.sadboifilip.mcapi.rest.endpoints;

import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

public class WhitelistEndpointGroup extends BaseEndpointGroup {

    public WhitelistEndpointGroup() {
        super();
    }

    @Override
    public void addEndpoints() {
        get("/", context -> {
            final List<PlayerResponse> list = this.app.getServer().getWhitelistedPlayers().stream()
                    .map(PlayerResponse::new).collect(Collectors.toList());
            context.json(list);
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
