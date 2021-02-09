package com.utabpars.gomgashteh.chat;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TicketResponseModel {
    @SerializedName("data")
    public List<Massage> massages;
    @SerializedName("enable")
    public String status;
    @SerializedName("response")
    public String response;
    @SerializedName("blocker")
    private boolean block;
    @SerializedName("isBlock")
    private boolean isBlockFromAnnouncer;
    @SerializedName("report_list")
    List<String> report_list=new ArrayList<>();
    @SerializedName("is_report")
    private boolean report;
    @SerializedName("user_confirmation")
    private boolean user_confirmation;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("respondent_user")
    private boolean respondent_user;
    @SerializedName("created_at")
    private String createAt;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isRespondent_user() {
        return respondent_user;
    }

    public void setRespondent_user(boolean respondent_user) {
        this.respondent_user = respondent_user;
    }

    public boolean isUser_confirmation() {
        return user_confirmation;
    }

    public void setUser_confirmation(boolean user_confirmation) {
        this.user_confirmation = user_confirmation;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public List<String> getReport_list() {
        return report_list;
    }

    public void setReport_list(List<String> report_list) {
        this.report_list = report_list;
    }

    public boolean isBlockFromAnnouncer() {
        return isBlockFromAnnouncer;
    }

    public void setBlockFromAnnouncer(boolean blockFromAnnouncer) {
        isBlockFromAnnouncer = blockFromAnnouncer;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public TicketResponseModel(List<Massage> massages) {
        this.massages = massages;
    }

    public List<Massage> getMassages() {
        return massages;
    }

    public void setMassages(List<Massage> massages) {
        this.massages = massages;
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
        @SerializedName("time")
        private int time;
        @SerializedName("answer_id")
        private int answer_id;

        public int getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(int answer_id) {
            this.answer_id = answer_id;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

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