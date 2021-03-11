package com.example.usersinfodb;

public class UserDataModel {
    private String id;
    private String userNameM;
    private String mobileNumberM;
    private String addressM;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUserNameM(String userNameM) {
        this.userNameM = userNameM;
    }

    public String getUserNameM() {
        return this.userNameM;
    }

    public void setMobileNumberM(String mobileNumberM) {
        this.mobileNumberM = mobileNumberM;
    }

    public String getMobileNumberM() {
        return this.mobileNumberM;
    }

    public void setAddressM(String addressM) {
        this.addressM = addressM;
    }

    public String getAddressM() {
        return this.addressM;
    }
}
