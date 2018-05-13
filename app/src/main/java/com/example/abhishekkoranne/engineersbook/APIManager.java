package com.example.abhishekkoranne.engineersbook;

/**
 * Created by Abhishek Koranne on 04-03-2018.
 */

import com.google.gson.JsonElement;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIManager {
    @FormUrlEncoded
    @POST(Constant.URL_LOGIN)
    Call<Map<String, Object>> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Constant.URL_REGISTER)
    Call<Map<String, Object>> register(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Constant.URL_GET_DETAIL)
    Call<Map<String, Object>> getDetails(@FieldMap Map<String, String> params);

//    @GET("/getarticle/1/7")
//    Map<String, Object> getArticles(@Query("page") int page, @Query("size") int size, Callback<Map<String, Object>> callback);

    @Multipart
    @POST(Constant.URL_ADD_ARTICLE)
    Call<Map<String, Object>> addarticles(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.URL_ADD_DOUBT)
    Call<Map<String, Object>> adddoubt(@PartMap Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST(Constant.URL_ADD_COMMENT)
    Call<Map<String, Object>> addcomment(@FieldMap Map<String, String> params);

    @GET(Constant.URL_GET_ARTICLES)
    Call<Map<String, Object>> getArticle(@Query("page") int page, @Query("size") int size);

    @GET(Constant.URL_GET_DOUBT)
    Call<Map<String, Object>> getdoubtlist(@Query("page") int page, @Query("size") int size);

    @GET("user/getcomment/{articleID}/")
    Call<Map<String, Object>> getComment(@Path(value = "articleID", encoded = true) int articleID,@Query("page") int page, @Query("size") int size);

    @GET("user/getanswer/{doubtID}/")
    Call<Map<String, Object>> getAnswer(@Path(value = "doubtID", encoded = true) int doubtID,@Query("page") int page, @Query("size") int size);

    @Multipart
    @POST(Constant.URL_STUD_REG)
    Call<Map<String, Object>> registerStudent(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.URL_FACULTY_REG)
    Call<Map<String, Object>> registerFaculty(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.URL_HOD_REG)
    Call<Map<String, Object>> registerHOD(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.URL_PRINCIPAL_REG)
    Call<Map<String, Object>> registerPrincipal(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST(Constant.URL_CHANCELLOR_REG)
    Call<Map<String, Object>> registerChancellor(@PartMap Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST(Constant.URL_FORGOT_PASS)
    Call<Map<String, Object>> forgotPass(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Constant.URL_FORGOT_PASS_TOKEN)
    Call<Map<String, Object>> checkToken(@FieldMap Map<String, String> params);

    @Multipart
    @POST(Constant.URL_ADD_ANSWER)
    Call<Map<String, RequestBody>> addanswer(@PartMap Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST(Constant.URL_UPDATE_LIKES)
    Call<Map<String, Object>> updateLikes(@FieldMap Map<String, Object> params);

    @GET(Constant.URL_GET_PROFILE_DATA)
    Call<Map<String, Object>> getProfileData(@Query("page") int page, @Query("size") int size);

    @FormUrlEncoded
    @POST(Constant.URL_GET_NETWORKLIST)
    Call<Map<String, Object>> getnetworklist(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(Constant.URL_GET_PLACED_STUD_DATA)
    Call<Map<String, Object>> getPlacedStudList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(Constant.URL_UPVOTE)
    Call<Map<String, Object>> sendUpvote(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(Constant.URL_DOWNVOTE)
    Call<Map<String, Object>> sendDownvote(@FieldMap Map<String, Object> params);

    //    Call<Map<String, Object>> registerStudent(@Part("name") RequestBody name);
    // Add all your api calls here...
}
