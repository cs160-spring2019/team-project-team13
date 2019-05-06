package com.example.petplant.camera.view;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface JsonPlaceHoldeApi {

    @POST("predict")
    @Multipart
    Call<DiseaseInfo> createPost(@PartMap Map<String, RequestBody> params);
}