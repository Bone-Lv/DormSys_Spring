package com.gdut.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrder {
    private Integer id;
    private String deviceType;
    private String description;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String studentId;
    private String dormNum;
    private String adminId;
    private String image;



    @Override
    public String toString() {
        return "RepairOrder{" +
                "id=" + id +
                ", deviceType='" + deviceType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", studentId='" + studentId + '\'' +
                ", dormNum='" + dormNum + '\'' +
                ", adminId='" + adminId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
