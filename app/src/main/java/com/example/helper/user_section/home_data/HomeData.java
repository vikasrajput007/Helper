
package com.example.helper.user_section.home_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeData {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
    @SerializedName("servicetypes")
    @Expose
    private List<Servicetype> servicetypes = null;
    @SerializedName("sliders")
    @Expose
    private List<Slider> sliders = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Servicetype> getServicetypes() {
        return servicetypes;
    }

    public void setServicetypes(List<Servicetype> servicetypes) {
        this.servicetypes = servicetypes;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
    }

}
