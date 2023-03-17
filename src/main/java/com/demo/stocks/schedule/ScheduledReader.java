package com.demo.stocks.schedule;

import com.demo.stocks.services.FileProcessor;
import com.demo.stocks.services.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Scheduled task to read the data from uploaded files
 * and put the data into redis server cache.
 */

@Component
public class ScheduledReader {

    private static final Logger log = LoggerFactory.getLogger(ScheduledReader.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${data.directory}")
    private String DATA_FOLDER;

    @Autowired
    private FileProcessor fileProcessor;

    /**
     * delay of 5 seconds between the finish time of an execution of a task
     * and the start time of the next execution of the task
     */
    @Scheduled(fixedDelay = 5000)
    public void reportCurrentTime() {
        log.info("Processing data directory at time: {}", dateFormat.format(new Date()));

        fileProcessor.processDataRoot(DATA_FOLDER);
    }
}
