
package com.example.helper.user_section.home_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servicetype {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("services")
    @Expose
    private List<Service_> services = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service_> getServices() {
        return services;
    }

    public void setServices(List<Service_> services) {
        this.services = services;
    }

}
