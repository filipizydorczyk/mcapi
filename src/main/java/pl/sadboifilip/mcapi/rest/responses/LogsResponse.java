package pl.sadboifilip.mcapi.rest.responses;

import java.util.Date;
import lombok.Getter;

public class LogsResponse {
    @Getter
    private String logs;
    @Getter
    private Date lastModified;

    public LogsResponse(String logs, Date lastModified) {
        this.logs = logs;
        this.lastModified = lastModified;
    }
}
