package com.Gosima.Sprout.OrderPackage;

public class OrderHistoryDTO {
    private String oldStatus;
    private String newStatus;
    private String updatedBy;
    private String updatedAt;

    public OrderHistoryDTO(String oldStatus, String newStatus, String updatedBy, String updatedAt) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
    // Getters & Setters


    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
