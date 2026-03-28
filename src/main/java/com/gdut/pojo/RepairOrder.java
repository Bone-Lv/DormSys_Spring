package com.gdut.pojo;

import java.time.LocalDateTime;

public class RepairOrder {
    private Integer id;
    private String deviceType;
    private String description;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String studentId;
    private String dormNum;
    private String adminId;

    public RepairOrder() {
    }

    public RepairOrder(String description,  String deviceType, String status, LocalDateTime createTime, LocalDateTime updateTime, String studentId, String dormNum, String adminId) {
        this.description = description;
        this.deviceType = deviceType;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.studentId = studentId;
        this.dormNum = dormNum;
        this.adminId = adminId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "id='" + id + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", studentId='" + studentId + '\'' +
                ", dormNum='" + dormNum + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }
}
