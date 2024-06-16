package com.restaurant.restaurant_backend.controllers.dopclass;

public class TableBookingRequest {
    private Integer numberTable;
    private Long clientId;
    private String clientName;
    private String clientPhoneNumber;

    public Integer getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(Integer numberTable) {
        this.numberTable = numberTable;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

}