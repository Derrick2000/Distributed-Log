# Tech Stack
Java (**JDK17**) + Springboot <br>
Note: Your IDE (Intellij recommended) needs to have a built-in server (Tomcat). <br>

# Architecture
HTTP Requests -> LogController -> LogService <br>

```
LogController 
    logService
    - getLog()
        - logService.getLog
    - addLog()
        - logService.addLog

LogService
    logMap (Storing log data)
    scheduler (Clean up expired logs)
    - removeExpiredEvents()
    - addLog()
    - getLogs()
    - getParsedTimeStamp()
    
LogDto: log Object
```

# Testing
Once SonatusPaApplication server is running:

### Get
`http://:8080/logs/get?service=testService1&startTime=2025-03-17T10:00:00Z&endTime=2025-03-17T11:00:00Z`

### Post
`http://localhost:8080/logs/add` +

```
Body: {
    "serviceName": "testService1",
    "timestamp"  : "2025-03-17T10:05:01Z",
    "message"    : "test15"
}
```