package com.utabpars.gomgashteh.chat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @SerializedName("attributes")
    List<attributes> attributes=new ArrayList<>();


    public List<ChatStatusModel.attributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ChatStatusModel.attributes> attributes) {
        this.attributes = attributes;
    }

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

    public class attributes{
        @SerializedName("id")
        private String id;
        @SerializedName("collection_id")
        private String collection_id;
        @SerializedName("name")
        private String name;
        @SerializedName("values")
        private List<String> value;
        @SerializedName("is_necessary")
        private boolean necessary;

        public String getCollection_id() {
            return collection_id;
        }

        public void setCollection_id(String collection_id) {
            this.collection_id = collection_id;
        }

        public attributes(String id, String name, List<String> value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public boolean isNecessary() {
            return necessary;
        }

        public void setNecessary(boolean necessary) {
            this.necessary = necessary;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
