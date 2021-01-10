package com.utabpars.gomgashteh.chat.reportchat;

import com.google.gson.annotations.SerializedName;

public class ReportModel {
    @SerializedName("status")
    private String status;
    @SerializedName("massage")
    private String massage;
    @SerializedName("is_report")
    private boolean report;

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
