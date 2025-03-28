package com.example.sonatus_pa;

import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.*;

@Service
public class LogService {
    private final ConcurrentHashMap<String, ConcurrentSkipListMap<Long, List<LogDto>>> logMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long EXPIRATION_TIME = 3600000; // 60 minutes

    public LogService() {
        scheduler.scheduleAtFixedRate(this::removeExpiredEvents, 1, 1, TimeUnit.MINUTES);
    }

    private void removeExpiredEvents() {
        long curTime = System.currentTimeMillis();
        long threshold = curTime - EXPIRATION_TIME;
        for (ConcurrentSkipListMap<Long, List<LogDto>> subMap: logMap.values()) {
            subMap.headMap(threshold, true).clear();
        }
    }

    public void addLog(String serviceName, long timestamp, LogDto dto) {
        logMap.computeIfAbsent(serviceName, i -> new ConcurrentSkipListMap<>())
                .computeIfAbsent(timestamp, j -> Collections.synchronizedList(new ArrayList<>()))
                .add(dto);
    }

    public List<LogDto> getLogs(String key, long startTime, long endTime) {
        List<LogDto> logList = new ArrayList<>();
        ConcurrentSkipListMap<Long, List<LogDto>> subMap = logMap.get(key);
        if (subMap != null) {
            NavigableMap<Long, List<LogDto>> range = subMap.subMap(startTime, endTime);
            for (List<LogDto> element: range.values()) logList.addAll(element);
        }
        return logList;
    }

    public long getParsedTimeStamp(String timestamp) {
        ZonedDateTime time = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
        return time.toInstant().toEpochMilli();
    }
}
