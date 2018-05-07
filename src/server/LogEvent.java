/*
 *  @author  Steven Turmel
 *  @version 2.4
 *  @date    May 6, 2018
 *  @project Capstone_Project
 *  @file    LogEvent.java
 *
 */
package server;

import java.util.Date;
import java.util.EventObject;


public class LogEvent extends EventObject {
    private Date logDate;
    private String logString;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public LogEvent(Object source, String logString) {
        super(source);
        this.logString = logString;
        logDate = new Date();
    }

    protected String getDate() {
        return logDate.toString();
    }

    protected String getLogString() {
        return logString;
    }
}
