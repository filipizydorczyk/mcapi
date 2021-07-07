package pl.sadboifilip.mcapi.rest;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.rest.endpoints.PlayersEndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RESTApp {
    private static Javalin app = null;

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
                });
            });

        }

        return RESTApp.app;
    }
}
