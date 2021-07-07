package pl.sadboifilip.mcapi.rest;

import java.util.HashSet;
import java.util.Set;

import io.javalin.Javalin;

public class RESTApp {
    private static Javalin app = null;

    public static Javalin getApp() {

        if (RESTApp.app == null) {

            RESTApp.app = Javalin.create();
            RESTApp.app.config.accessManager((handler, ctx, permittedRoles) -> {
                final UserRoles userRole = UserRoles.getUserRole(ctx);
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx);
                } else {
                    ctx.status(401).result("Unauthorized");
                }
            });

            app.get("/", context -> context.result("Hi"));

        }

        return RESTApp.app;
    }
}
