package com.example.sonatus_pa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("/get")
    public ResponseEntity<List<LogDto>> getLog(@RequestParam String service,
                                               @RequestParam String startTime,
                                               @RequestParam String endTime) {
        List<LogDto> list = logService.getLogs(service, logService.getParsedTimeStamp(startTime), logService.getParsedTimeStamp(endTime));
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @RequestMapping("/add")
    public ResponseEntity<String> addLog(@RequestBody LogDto dto) {
        String service = dto.getServiceName();
        logService.addLog(service, logService.getParsedTimeStamp(dto.getTimestamp()), dto);
        return new ResponseEntity<>("A new log in " + service + " has been added", HttpStatus.CREATED);
    }
}
