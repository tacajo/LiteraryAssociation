package com.paypal.config.logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.spi.FilterReply;

import java.util.Arrays;
import java.util.List;

public class TraceDebugLogsConfig extends ch.qos.logback.core.filter.AbstractMatcherFilter{

    @Override
    public FilterReply decide(Object event)
    {
        if (!isStarted())
        {
            return FilterReply.NEUTRAL;
        }

        LoggingEvent loggingEvent = (LoggingEvent) event;
        List<Level> eventsToKeep = Arrays.asList(Level.TRACE, Level.DEBUG);
        if (eventsToKeep.contains(loggingEvent.getLevel()))
        {
            return FilterReply.NEUTRAL;
        }
        else
        {
            return FilterReply.DENY;
        }
    }
}