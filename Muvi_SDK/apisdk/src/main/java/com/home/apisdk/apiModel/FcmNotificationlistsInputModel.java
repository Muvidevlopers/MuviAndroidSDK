package com.home.apisdk.apiModel;

/**
 * Created by Android on 10/29/2017.
 */

public class FcmNotificationlistsInputModel {

    private String authToken,device_id;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}

