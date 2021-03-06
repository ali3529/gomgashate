package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatsModel {
    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private List<Tickets> ticketList;
    @SerializedName("system_messages")
    private String system_messages;

    public String getSystem_messages() {
        return system_messages;
    }

    public void setSystem_messages(String system_messages) {
        this.system_messages = system_messages;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Tickets> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Tickets> ticketList) {
        this.ticketList = ticketList;
    }

    public class Tickets{
        @SerializedName("ticket_id")
        private String ticket_id;
        @SerializedName("user_id")
        private String user_id;
        @SerializedName("announcement_title")
        private String announcement_title;
        @SerializedName("message_time")
        private String message_time;
        @SerializedName("announcement_id")
        private String announcement_id;
        @SerializedName("pictures")
        private String picture;
        @SerializedName("block")
        private boolean block;
        @SerializedName("sum")
        private int massageNumber;

        public int getMassageNumber() {
            return massageNumber;
        }

        public void setMassageNumber(int massageNumber) {
            this.massageNumber = massageNumber;
        }

        public boolean isBlock() {
            return block;
        }

        public void setBlock(boolean block) {
            this.block = block;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }


        public String getAnnouncement_id() {
            return announcement_id;
        }

        public void setAnnouncement_id(String announcement_id) {
            this.announcement_id = announcement_id;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAnnouncement_title() {
            return announcement_title;
        }

        public void setAnnouncement_title(String announcement_title) {
            this.announcement_title = announcement_title;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }
    }
}
