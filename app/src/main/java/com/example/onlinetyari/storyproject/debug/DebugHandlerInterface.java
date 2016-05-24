package com.example.onlinetyari.storyproject.debug;

/**
 * Created by Siddharth Verma on 24/5/16.
 */
public interface DebugHandlerInterface {

    void Log(String logType, String log);
    void LogException(String logType, Exception exception);
}
