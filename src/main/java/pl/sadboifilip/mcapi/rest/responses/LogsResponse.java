package pl.sadboifilip.mcapi.rest.responses;

import java.util.Date;

public class LogsResponse {
    private String logs;
    private Date lastModified;

    public LogsResponse(String logs, Date lastModified) {
        this.logs = logs;
        this.lastModified = lastModified;
    }

    public String getLogs() {
        return logs;
    }

    public Date getLastModified() {
        return lastModified;
    }

}
