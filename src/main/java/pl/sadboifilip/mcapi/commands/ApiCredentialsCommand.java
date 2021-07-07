package pl.sadboifilip.mcapi.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.sadboifilip.mcapi.db.TokenKeeper;

public class ApiCredentialsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            TokenKeeper tokenKeeper = TokenKeeper.getTokenKeeper();

            UUID apiToken = tokenKeeper.getPlayerApiToken(player.getUniqueId());
            tokenKeeper.saveData(TokenKeeper.TOKEN_KEEPER_FILE);

            player.sendMessage(apiToken.toString());
        }
        return true;
    }

}
