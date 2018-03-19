package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For CheckGeoBlockCountryAsynTask
 *
 * @author MUVI
 */

public class CheckGeoBlockInputModel {

    String authToken;
    String ip;

    /**
     * This Method is use to Get the IP Address
     *
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * This Method is use to Set the IP Address
     *
     * @param ip For Setting The IP Address
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

}
