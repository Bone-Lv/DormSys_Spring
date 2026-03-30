package com.gdut.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOperationLog {
    private Integer id;
    private String adminId;
    private String operation;
    private String method;
    private String url;
    private String params;
    private String ip;
    private LocalDateTime operationTime;

    public AdminOperationLog(String adminId, String operation, String httpMethod, String url, String params, String ip) {
        this.adminId = adminId;
        this.operation = operation;
        this.method = httpMethod;
        this.url = url;
        this.params = params;
        this.ip = ip;
    }
}
