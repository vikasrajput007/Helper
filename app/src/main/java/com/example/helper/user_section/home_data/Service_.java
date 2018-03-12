
package com.example.helper.user_section.home_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service_ {

    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("thumbnails")
    @Expose
    private String thumbnails;

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

}
