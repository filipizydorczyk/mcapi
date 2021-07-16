package pl.sadboifilip.mcapi.rest.responses;

import org.bukkit.entity.Player;

public class PlayerResponse {

    private String id;
    private String name;

    public PlayerResponse(Player player) {
        this.id = player.getUniqueId().toString();
        this.name = player.getName();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
