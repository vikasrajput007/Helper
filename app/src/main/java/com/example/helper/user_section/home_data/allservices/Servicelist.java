package com.example.helper.user_section.home_data.allservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servicelist {

    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("servicename")
    @Expose
    private String servicename;
    @SerializedName("icons")
    @Expose
    private String icons;

    public String getServiceid() {
        return serviceid;
    }

    public String getServicename() {
        return servicename;
    }

    public String getIcons() {
        return icons;
    }
}
