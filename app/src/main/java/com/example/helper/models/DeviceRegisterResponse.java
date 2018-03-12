package com.example.helper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 24-02-2018.
 */

public class DeviceRegisterResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("userid")
    @Expose
    private int userid;

    public int getStatus() {
        return status;
    }

}
