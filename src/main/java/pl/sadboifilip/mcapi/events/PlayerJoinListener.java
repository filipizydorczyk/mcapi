package pl.sadboifilip.mcapi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.sadboifilip.mcapi.ApplicationConfig;
import pl.sadboifilip.mcapi.rest.SseClientService;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);

        try {
            final SseClientService service = context.getBean(SseClientService.class);
            service.sendEventToClients(SseClientService.PLAYER_LOGGED_IN, evt.getPlayer().getName());
        } finally {
            context.close();
        }
    }
}
