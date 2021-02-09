package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("data")
    private Data data;
    @SerializedName("massage")
    private String message;
    @SerializedName("response")
    private String response;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public class Data{
        @SerializedName("id")
        private int id;
        @SerializedName("first_name")
        private String first_name;
        @SerializedName("last_name")
        private String last_name;
        @SerializedName("full_name")
        private String full_name;
        @SerializedName("phone_num")
        private String phone_num;
        @SerializedName("national_code")
        private Object national_code;
        @SerializedName("gender")
        private Object gender;
        @SerializedName("birthday")
        private Object birthday;
        @SerializedName("tell")
        private Object tell;
        @SerializedName("email")
        private Object email;
        @SerializedName("province")
        private Object province;
        @SerializedName("city")
        private Object city;
        @SerializedName("address")
        private Object address;
        @SerializedName("postal_code")
        private Object postal_code;
        @SerializedName("user_type")
        private String userType;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
        }

        public Object getNational_code() {
            return national_code;
        }

        public void setNational_code(Object national_code) {
            this.national_code = national_code;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getTell() {
            return tell;
        }

        public void setTell(Object tell) {
            this.tell = tell;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(Object postal_code) {
            this.postal_code = postal_code;
        }
    }




}
