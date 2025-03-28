package com.example.sonatus_pa;

public class LogDto {
    private String serviceName;
    private String timestamp;
    private String message;

    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getServiceName() { return serviceName; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getTimestamp() { return timestamp; }
    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return message; }

}
