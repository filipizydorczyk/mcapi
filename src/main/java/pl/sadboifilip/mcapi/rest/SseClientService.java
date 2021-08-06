package pl.sadboifilip.mcapi.rest;

import java.util.ArrayList;
import java.util.List;

import io.javalin.http.sse.SseClient;

public class SseClientService {

    private static SseClientService instance;
    public static String PLAYER_LOGGED_IN = "player-logged-in";
    public static String PLAYER_LOGGED_OUT = "player-logged-out";

    private List<SseClient> clients;

    private SseClientService() {
        this.clients = new ArrayList<>();
    }

    public static SseClientService getInstance() {
        if (SseClientService.instance == null) {
            SseClientService.instance = new SseClientService();
        }
        return SseClientService.instance;
    }

    public void registerClient(SseClient client) {
        this.clients.add(client);
    }

    public void unregisterClient(SseClient client) {
        this.clients.remove(client);
    }

    public void sendEventToClients(String event, String data) {
        clients.forEach(c -> {
            c.sendEvent(event, data);
        });
    }

}
