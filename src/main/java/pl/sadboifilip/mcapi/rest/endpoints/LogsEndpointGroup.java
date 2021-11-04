package pl.sadboifilip.mcapi.rest.endpoints;

import static io.javalin.apibuilder.ApiBuilder.get;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import pl.sadboifilip.mcapi.rest.UserRoles;
import pl.sadboifilip.mcapi.rest.responses.LogsResponse;

public class LogsEndpointGroup extends BaseEndpointGroup {
    private static final String LOGS_PATH = "/logs/latest.log";

    public LogsEndpointGroup() {
        super();
    }

    @Override
    public void addEndpoints() {
        get("/", context -> {
            final String logFilePath = this.app.getServer().getWorldContainer().getAbsolutePath()
                    + LogsEndpointGroup.LOGS_PATH;
            final Path logFile = Path.of(logFilePath);

            final String logs = Files.readString(logFile);
            final Date lastModified = new Date(Files.getLastModifiedTime(logFile).toMillis());

            context.json(new LogsResponse(logs, lastModified));

        }, UserRoles.createPermissions(UserRoles.OP_PLAYER));
    }
}
