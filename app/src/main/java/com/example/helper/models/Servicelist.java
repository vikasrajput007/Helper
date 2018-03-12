
package com.example.helper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servicelist {

    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("servicename")
    @Expose
    private String servicename;

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

}
