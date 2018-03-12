package com.example.helper.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileData {

    @SerializedName("vendorid")
    @Expose
    private int vendorid;
    @SerializedName("profilepic")
    @Expose
    private String profilePic;
    @SerializedName("idproofs")
    @Expose
    private List<String> idproofs = null;
    @SerializedName("work_photos")
    @Expose
    private List<String> workPhotos = null;
    @SerializedName("lattitude")
    @Expose
    private String lattitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;


    public int getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }


    public ProfileData(int vendorid,  List<String> idproofs, List<String> workPhotos,String profilePic, String lattitude, String longitude)
    {
        this.vendorid = vendorid;
        this.profilePic = profilePic;
        this.idproofs = idproofs;
        this.workPhotos = workPhotos;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

//    public Integer getVendorid() {
//        return vendorid;
//    }
//
//    public void setVendorid(int vendorid) {
//        this.vendorid = vendorid;
//    }
//
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
//
//    public List<String> getIdproofs() {
//        return idproofs;
//    }
//
//    public void setIdproofs(List<String> idproofs) {
//        this.idproofs = idproofs;
//    }
//
//    public List<String> getWorkPhotos() {
//        return workPhotos;
//    }
//
//    public void setWorkPhotos(List<String> workPhotos) {
//        this.workPhotos = workPhotos;
//    }
//
//    public String getLattitude() {
//        return lattitude;
//    }
//
//    public void setLattitude(String lattitude) {
//        this.lattitude = lattitude;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }

//    @Override
//    public String toString() {
//        return "ProfileData{" +
//                "vendorid=" + vendorid +
//                ", profilePic='" + profilePic + '\'' +
//                ", idproofs=" + idproofs +
//                ", workPhotos=" + workPhotos +
//                ", lattitude='" + lattitude + '\'' +
//                ", longitude='" + longitude + '\'' +
//                '}';
//    }
}

