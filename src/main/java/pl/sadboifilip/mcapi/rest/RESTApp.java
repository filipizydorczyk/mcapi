package pl.sadboifilip.mcapi.rest;

import static io.javalin.apibuilder.ApiBuilder.path;

import io.javalin.Javalin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.sadboifilip.mcapi.ApplicationConfig;
import pl.sadboifilip.mcapi.rest.endpoints.LogsEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.PlayersEndpointGroup;
import pl.sadboifilip.mcapi.rest.endpoints.WhitelistEndpointGroup;

public class RESTApp extends Javalin {

    @Override
    public Javalin start(int port) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(RESTApp.class.getClassLoader());
        super.start(port);
        Thread.currentThread().setContextClassLoader(classLoader);

        this.config.accessManager((handler, ctx, permittedRoles) -> {
            final UserRoles userRole = UserRoles.getUserRole(ctx);
            if (permittedRoles.contains(userRole)) {
                handler.handle(ctx);
            } else {
                ctx.status(401).result("Unauthorized");
            }
        });

        this.routes(() -> {
            path("/api/v1/", () -> {
                path("players", new PlayersEndpointGroup());
                path("whitelist", new WhitelistEndpointGroup());
                path("logs", new LogsEndpointGroup());
            });
        });

        this.sse("/events", client -> {
            final AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(ApplicationConfig.class);

            try {
                final SseClientService service = context.getBean(SseClientService.class);
                service.registerClient(client);
                client.onClose(() -> service.unregisterClient(client));
            } finally {
                context.close();
            }
        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));
        return this;
    }

}
