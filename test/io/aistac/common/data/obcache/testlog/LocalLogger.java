/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * @(#)LocalLogger.java
 *
 * Copyright:	Copyright (c) 2016
 * Company:		Oathouse.com Ltd
 */
package io.aistac.common.data.obcache.testlog;

import io.aistac.common.canonical.queue.ObjectBeanQueue;
import io.aistac.common.canonical.log.LoggerBean;
import io.aistac.common.canonical.log.LoggerLevel;
import io.aistac.common.canonical.log.LoggerQueueService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * The {@code LocalLogger} Class
 *
 * @author Darryl Oatridge
 * @version 1.00 15-Apr-2016
 */
public class LocalLogger {
    private static boolean isLogging = true;
    private static boolean isRunning = true;

    public static void log() {
         ExecutorService executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        //setup logging
        Callable<Boolean> worker = () -> {
                    return writeLogs();
        };
        executor.submit(worker);
    }

    private static boolean writeLogs() {
        LoggerQueueService queueService = LoggerQueueService.getInstance();
        queueService.setLogLevel(LoggerLevel.TRACE);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        try {
            while(true) {
                LoggerBean log = queueService.take();
                String output = (formatter.format(new Date()) + " " + LoggerLevel.level(log.getId()) + " " + log.getTag() + " " + log.getMessage()).trim();
                System.out.println(output);
            }
        } catch(InterruptedException ex) {
            // fall through
        }
        return true;
    }

    private LocalLogger() {
    }

}
