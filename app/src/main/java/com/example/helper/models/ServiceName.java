
package com.example.helper.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceName {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("servicelist")
    @Expose
    private List<Servicelist> servicelist = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Servicelist> getServicelist() {
        return servicelist;
    }

    public void setServicelist(List<Servicelist> servicelist) {
        this.servicelist = servicelist;
    }

}
