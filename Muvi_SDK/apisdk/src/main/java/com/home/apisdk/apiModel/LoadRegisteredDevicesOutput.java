package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For LoadRegisteredDevicesAsync
 *
 * @author MUVI
 */

public class LoadRegisteredDevicesOutput {
    String device;

    /**
     * This Method is use to Get the Device
     *
     * @return device
     */
    public String getDevice() {
        return device;
    }

    /**
     * This Method is use to Set the Device
     *
     * @param device For Setting The Device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * This Method is use to Get the Device Information
     *
     * @return device_info
     */
    public String getDevice_info() {
        return device_info;
    }

    /**
     * This Method is use to Set the Device Information
     *
     * @param device_info For Setting The Device Information
     */
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    /**
     * This Method is use to Get the Flag
     *
     * @return flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * This Method is use to Set the Flag
     *
     * @param flag For Setting The Flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    String device_info;
    String flag;
}
