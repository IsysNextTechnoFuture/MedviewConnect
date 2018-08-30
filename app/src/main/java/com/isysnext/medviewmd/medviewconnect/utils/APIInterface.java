package com.isysnext.medviewmd.medviewconnect.utils;

import com.isysnext.medviewmd.medviewconnect.modelDr.PatientVisitDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.UserDTO;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface
{
    String SIGN_IN = "api/ios/auth";
    String SIGN_UP = "api/ios/patient/register";
    String UPLOAD_IMAGE = "api/patient/avatar";
    String FORGOT_PASSWORD = "api/ios/auth/forgotpassword";
    String LOAD_PROVIDER = "api/ios/loadproviders";
    String GET_VISIT = "/api/ios/provider/call_list";
    AppSession appSession = null;

    @FormUrlEncoded
    @POST(GET_VISIT)
    Call<PatientVisitDTO> vistPatient(@Field("provider_id") String provider_id, @Field("speciality") String speciality, @Field("partner_id") String partnerid);


    @FormUrlEncoded
    @POST(SIGN_IN)
    Call<UserDTO> signIn(@Field("email") String email, @Field("password") String password, @Field("partner_id") String partnerid);

    @FormUrlEncoded
    @POST(SIGN_UP)
    Call<UserDTO> signUp(@Field("first_name") String first_name, @Field("last_name") String last_name,
                         @Field("security_question") String security_question, @Field("zip_code") int zip_code,
                         @Field("security_answer") String security_answer, @Field("partner_id") int partner_id,
                         @Field("birth_date") int birth_date, @Field("address_line1") String address_line1,
                         @Field("password") String password,
                         @Field("email") String email, @Field("gender") String gender,
                         @Field("state") String state, @Field("city") String city,
                         @Field("repeat_password") String repeat_password, @Field("phone") String phone,
                         @Field("residenceType") String residenceType, @Field("mobileno") String mobileno);
    @FormUrlEncoded
    @POST(SIGN_UP)
    Call<UserDTO> signUpWithShipment(@Field("first_name") String first_name, @Field("last_name") String last_name,
                                     @Field("security_question") String security_question, @Field("zip_code") int zip_code,
                                     @Field("security_answer") String security_answer, @Field("partner_id") int partner_id,
                                     @Field("birth_date") int birth_date, @Field("address_line1") String address_line1,
                                     @Field("password") String password,
                                     @Field("email") String email, @Field("gender") String gender,
                                     @Field("state") String state, @Field("city") String city,
                                     @Field("repeat_password") String repeat_password, @Field("phone") String phone,
                                     @Field("residenceType") String residenceType, @Field("mobileno") String mobileno,
                                     @Field("shippingaddressLine1") String shippingaddressLine1, @Field("shippingcity") String shippingcity,
                                     @Field("shippingstate") String shippingstate, @Field("shippingzipcode") String shippingzipcode);
    @FormUrlEncoded
    @POST(UPLOAD_IMAGE)
    Call<UserDTO> uploadImge(@Field("avatar") String avatar);

    @FormUrlEncoded
    @POST(FORGOT_PASSWORD)
    Call<UserDTO> forgotPassword(@Field("email") String email, @Field("partner_id") String partnerid);


}
