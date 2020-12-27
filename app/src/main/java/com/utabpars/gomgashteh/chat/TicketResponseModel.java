package com.utabpars.gomgashteh.chat;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketResponseModel {
    @SerializedName("data")
    public List<Massage> massages;
    @SerializedName("phone_sender")
    public String phone_sender;
    @SerializedName("phone_receiver")
    public String phone_receiver;
    @SerializedName("enable")
    public String status;
    @SerializedName("response")
    public String response;

    public TicketResponseModel(List<Massage> massages) {
        this.massages = massages;
    }

    public List<Massage> getMassages() {
        return massages;
    }

    public void setMassages(List<Massage> massages) {
        this.massages = massages;
    }

    public String getPhone_sender() {
        return phone_sender;
    }

    public void setPhone_sender(String phone_sender) {
        this.phone_sender = phone_sender;
    }

    public String getPhone_receiver() {
        return phone_receiver;
    }

    public void setPhone_receiver(String phone_receiver) {
        this.phone_receiver = phone_receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static class Massage {
        public Massage(String message, String type,String status ) {
            this.message = message;
            this.type = type;
            this.status=status;
        }

        public Massage(String message, String type,String status,String pictures) {
            this.status = status;
            this.message = message;
            this.pictures = pictures;
            this.type = type;
        }

        @SerializedName("announcement")
        public String announcement;
        @SerializedName("ticket_id")
        public String ticket_id;
        @SerializedName("status")
        public String status;
        @SerializedName("message")
        public String message;
        @SerializedName("pictures")
        public String pictures;
        @SerializedName("sender_id")
        public String sender_id;
        @SerializedName("receiver_id")
        public String receiver_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("type")
        public String type;

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}