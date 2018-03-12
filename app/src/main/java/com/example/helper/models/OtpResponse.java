package com.example.helper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 17-02-2018.
 */

public class OtpResponse {


    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userid")
    @Expose
    private String userid;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserid() {
        return userid;
    }
}
