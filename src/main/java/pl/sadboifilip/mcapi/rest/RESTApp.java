package pl.sadboifilip.mcapi.rest;

import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import pl.sadboifilip.mcapi.rest.endpoints.LogsEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.PlayersEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.WhitelistEndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RESTApp {
    private static Javalin app;

    public static Javalin getApp() {

        if (RESTApp.app == null) {

            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(RESTApp.class.getClassLoader());
            RESTApp.app = Javalin.create().start(7000);
            Thread.currentThread().setContextClassLoader(classLoader);

            RESTApp.app.config.accessManager((handler, ctx, permittedRoles) -> {
                final UserRoles userRole = UserRoles.getUserRole(ctx);
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx);
                } else {
                    ctx.status(401).result("Unauthorized");
                }
            });

            app.routes(() -> {
                path("/api/v1/", () -> {
                    path("players", new PlayersEndpointGroup());
                    path("whitelist", new WhitelistEndpointGroup());
                    path("logs", new LogsEndpointGroup());
                });
            });

            app.sse("/events", client -> {
                client.sendEvent("connected", "Hello, SSE");
                client.onClose(() -> System.out.println("Client disconnected"));
            }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        }

        return RESTApp.app;
    }

}
