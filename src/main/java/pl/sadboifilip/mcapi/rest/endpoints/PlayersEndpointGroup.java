package pl.sadboifilip.mcapi.rest.endpoints;

import io.javalin.apibuilder.EndpointGroup;
import pl.sadboifilip.mcapi.App;
import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.PlayerResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersEndpointGroup implements EndpointGroup {

    @Override
    public void addEndpoints() {
        get("logged", context -> {
            final List<PlayerResponse> list = App.getPlugin(App.class).getServer().getOnlinePlayers().stream()
                    .map(PlayerResponse::new).collect(Collectors.toList());
            context.json(list);
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

    }

}
