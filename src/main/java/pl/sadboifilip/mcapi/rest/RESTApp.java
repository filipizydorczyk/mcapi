package pl.sadboifilip.mcapi.rest;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.ApplicationConfig;
import pl.sadboifilip.mcapi.rest.endpoints.LogsEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.PlayersEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.WhitelistEndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
                final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                        ApplicationConfig.class);

                try {
                    final SseClientService service = context.getBean(SseClientService.class);
                    service.registerClient(client);
                    client.onClose(() -> service.unregisterClient(client));
                } finally {
                    context.close();
                }
            }, UserRoles.createPermissions(UserRoles.OP_PLAYER));

        }

        return RESTApp.app;
    }

}
