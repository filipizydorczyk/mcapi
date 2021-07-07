package pl.sadboifilip.mcapi.rest;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.javalin.core.security.Role;
import io.javalin.http.Context;
import pl.sadboifilip.mcapi.db.TokenKeeper;

public enum UserRoles implements Role {
    OP_PLAYER, WHITELISTED_PLAYER, BANNED_PLAYER, NORMAL_PLAYER, NOT_EXISTING_PLAYER;

    public static UserRoles getUserRole(Context ctx) {
        final String token = ctx.req.getHeader("Auth");
        final TokenKeeper tokenKeeper = TokenKeeper.getTokenKeeper();

        final UUID playerId = tokenKeeper.getTokensPlayerId(UUID.fromString(token));

        if (playerId == null) {
            return UserRoles.NOT_EXISTING_PLAYER;
        }

        final OfflinePlayer player = Bukkit.getOfflinePlayer(playerId);

        if (player.isOp()) {
            return UserRoles.OP_PLAYER;
        }

        if (player.isWhitelisted()) {
            return UserRoles.WHITELISTED_PLAYER;
        }

        if (player.isBanned()) {
            return UserRoles.BANNED_PLAYER;
        }

        return UserRoles.NORMAL_PLAYER;
    }
}
