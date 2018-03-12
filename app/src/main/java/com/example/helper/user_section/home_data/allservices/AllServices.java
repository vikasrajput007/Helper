
package com.example.helper.user_section.home_data.allservices;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllServices {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("servicelist")
    @Expose
    private List<Servicelist> servicelist = null;

    public int getStatus() {
        return status;
    }


    public List<Servicelist> getServicelist() {
        return servicelist;
    }


}
