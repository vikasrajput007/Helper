
package com.example.helper.user_section.home_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("thumbnails")
    @Expose
    private String thumbnails;
    @SerializedName("servicetypeid")
    @Expose
    private String servicetypeid;

    public String getServiceid() {
        return serviceid;
    }


    public String getName() {
        return name;
    }


    public String getIcon() {
        return icon;
    }


    public String getThumbnails() {
        return thumbnails;
    }


    public String getServicetypeid() {
        return servicetypeid;
    }


}
