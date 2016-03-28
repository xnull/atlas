package bynull.poi.service;

import bynull.poi.dao.PoiRepository;
import bynull.poi.dto.PoiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Poi manager works async out of request/response scope
 * Created by null on 3/25/16.
 */
@Component
public class PoiManager {
    private static final Logger log = LoggerFactory.getLogger(PoiManager.class);

    /**
     * The ExecutorService is easy to use in the simple cases.
     * In more complicated cases can be used akka, another separate micro-service, etc.
     */
    private final ExecutorService TASK_EXECUTOR = Executors.newCachedThreadPool();

    @Autowired
    private PoiRepository poiRepository;

    public void add(PoiDto poi) {
        log.debug("Save a POI asynchronously: {}", poi);

        CompletableFuture.runAsync(() -> {
            //very expensive computational operation
            for (int i = 0; i < 500; i++) {
              Thread.yield();
            }
            poiRepository.add(poi);
        }, TASK_EXECUTOR);
    }

    public void awaitTermination() throws InterruptedException {
        TASK_EXECUTOR.awaitTermination(1, TimeUnit.SECONDS);
    }
}
