package pl.sadboifilip.mcapi.rest;

import io.javalin.Javalin;

public class RESTApp {
    private static Javalin app = null;

    public static Javalin getApp() {

        if (RESTApp.app == null) {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
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

            app.get("/", context -> context.result("Hi"),
                    UserRoles.createPermissions(UserRoles.OP_PLAYER, UserRoles.BANNED_PLAYER));

        }

        return RESTApp.app;
    }
}
