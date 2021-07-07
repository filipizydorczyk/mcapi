package pl.sadboifilip.mcapi.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class TokenKeeper implements Serializable {

    public static final String TOKEN_KEEPER_FILE = "tokens.ser";

    private static transient final long serialVersionUID = 1L;
    private final HashMap<UUID, UUID> tokens = new HashMap<>();

    public static TokenKeeper getTokenKeeper() {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(
                    new GZIPInputStream(new FileInputStream(TokenKeeper.TOKEN_KEEPER_FILE)));

            TokenKeeper tokenKeeper = (TokenKeeper) in.readObject();
            in.close();

            if (tokenKeeper == null) {
                tokenKeeper = new TokenKeeper();
            }

            return tokenKeeper;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveData(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            GZIPOutputStream gzOut = new GZIPOutputStream(fileOut);
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(gzOut);
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UUID getPlayerApiToken(UUID playerId) {
        UUID apiToken = this.tokens.get(playerId);
        if (apiToken == null) {
            apiToken = UUID.randomUUID();
            this.tokens.put(playerId, apiToken);
        }

        return apiToken;
    }

}
