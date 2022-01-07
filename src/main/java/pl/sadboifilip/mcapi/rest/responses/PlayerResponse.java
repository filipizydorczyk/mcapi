package pl.sadboifilip.mcapi.rest.responses;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerResponse {

    @Getter
    private String id;
    @Getter
    private String name;

    public PlayerResponse(Player player) {
        this.id = player.getUniqueId().toString();
        this.name = player.getName();
    }

    public PlayerResponse(OfflinePlayer player) {
        this.id = player.getUniqueId().toString();
        this.name = player.getName();
    }

}
