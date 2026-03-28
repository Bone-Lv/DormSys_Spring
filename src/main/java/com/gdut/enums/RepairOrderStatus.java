package com.gdut.enums;

public enum RepairOrderStatus {
    PENDING("待处理", 1),
    IN_PROGRESS("处理中", 2),
    COMPLETED("已完成", 3),
    CANCELLED("已取消", 4);

    private final String description;
    private final int priority;

    RepairOrderStatus(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public static RepairOrderStatus fromDescription(String description) {
        if (description == null) {
            return null;
        }
        for (RepairOrderStatus status : values()) {
            if (status.description.equals(description)) {
                return status;
            }
        }
        return null;
    }

    public static int compare(String status1, String status2) {
        RepairOrderStatus s1 = fromDescription(status1);
        RepairOrderStatus s2 = fromDescription(status2);
        
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return 1;
        if (s2 == null) return -1;
        
        return Integer.compare(s1.getPriority(), s2.getPriority());
    }

    public static int getPriorityValue(String status) {
        RepairOrderStatus repairOrderStatus = fromDescription(status);
        return repairOrderStatus != null ? repairOrderStatus.getPriority() : 99;
    }
}
