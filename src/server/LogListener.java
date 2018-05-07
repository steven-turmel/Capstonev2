package server;

import java.util.EventListener;

public interface LogListener extends EventListener {
    void logHandler(LogEvent e);
}
