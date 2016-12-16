package com.mizofumi.androidserialsample2.List;

/**
 * Created by mizofumi on 2016/12/16.
 */

public class DeviceList {
    String name;
    String macAddress;

    public DeviceList(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }
}
