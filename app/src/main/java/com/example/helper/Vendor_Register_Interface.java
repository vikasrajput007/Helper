package com.example.helper;

import com.example.helper.models.DeviceRegisterResponse;
import com.example.helper.models.OtpResponse;
import com.example.helper.models.ProfileData;
import com.example.helper.models.RegisterResponse;
import com.example.helper.models.VendorResponseBean;
import com.example.helper.user_section.home_data.HomeData;
import com.example.helper.user_section.home_data.UserRequestResponse;
import com.example.helper.user_section.home_data.allservices.AllServices;
import com.example.helper.utils.DeclineType;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by payal on 2/8/2018.
 */

public interface Vendor_Register_Interface {

    //  to register the device
    @GET("apis/user/deviceregister")
    Call<DeviceRegisterResponse>  regosterDevice(@Query("id") String id, @Query("deviceid") String deviceid, @Query("token") String token,@Query("usertype")String usertype);

    // vendor registration
    @GET("apis/user/deviceregister")
    Call<DeviceRegisterResponse>  regosterDevicevendor(@Query("serviceid") String serviceid,@Query("id") String id, @Query("deviceid") String deviceid, @Query("token") String token,@Query("usertype")String usertype);


    @GET("register")
    Call<VendorResponseBean> saveData(@Query("mobileno") String vendormobileno, @Query("name") String vendorname, @Query("serviceids") String serviceids, @Query("lattitude") String lattitude, @Query("longitude") String longitude);

    @POST("addprofile")
    Call<ProfileData>sendData(@Body ProfileData profileData);


    @GET("register")
    Call<VendorResponseBean> getVendorData();


    // register_user
    @GET("apis/user/register")
    Call<RegisterResponse> registerUser(@Query("name") String name ,@Query("mobileno") String mobileno, @Query("lattitude") String lattitude, @Query("longitude") String longitude);

    // verify user
    @GET("apis/user/otpverify")
    Call<OtpResponse> getVerified(@Query("mobileno") String mobileno, @Query("otp") String otp);

    // dashboard api
    @GET("apis/get/services")
    Call<HomeData> getHomeData();

    // To get all srvices
    @GET("apis/vendors/services/list")
    Call<AllServices> getAllServices(@Query("id") String id);

    // To send the request to vendor
    @GET("apis/user/servicerequest")
    Call<UserRequestResponse> requestToVendor(@Query("userid") String userid,@Query("serviceid") String serviceid,@Query("lattitude") String lattitude,@Query("longitude") String longitude);

    // Accept By Vendor longitue 
    @GET("apis/vendor/accept")
    Call<ResponseBody> acceptRequest(@Query("taskid") String taskid,@Query("lattitude")String vendorlat,@Query("longitude") String vendorlong);

    @GET("apis/vendor/decline")
    Call<ResponseBody> declineRequest(@Query("type") DeclineType type, @Query("taskid") String taskid, @Query("lattitude")String vendorlat,
                                      @Query("longitude") String vendorlong,@Query("userid")String userid,
                                      @Query("serviceid") String serviceid ,@Query("vendorid")String vendorid);



}

