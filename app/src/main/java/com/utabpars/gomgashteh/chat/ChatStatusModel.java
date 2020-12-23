package com.utabpars.gomgashteh.chat;

import com.google.gson.annotations.SerializedName;

public class ChatStatusModel {
    @SerializedName("block_user")
    private String block_status;
    @SerializedName("statusTicket")
    private String status_ticket;
    @SerializedName("massage")
    private String massage;
    @SerializedName("status")
    private String status;
    @SerializedName("ticket_id")
    private String ticket_id;

    public String getBlock_status() {
        return block_status;
    }

    public void setBlock_status(String block_status) {
        this.block_status = block_status;
    }

    public String getStatus_ticket() {
        return status_ticket;
    }

    public void setStatus_ticket(String status_ticket) {
        this.status_ticket = status_ticket;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}
