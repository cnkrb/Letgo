package com.cenkkaraboa.myapplication.Interfaces;



import com.cenkkaraboa.myapplication.models.CityModel;
import com.cenkkaraboa.myapplication.models.DistrictModel;
import com.cenkkaraboa.myapplication.models.FavoriteModel;
import com.cenkkaraboa.myapplication.models.Product;
import com.cenkkaraboa.myapplication.models.ProfileModel;
import com.cenkkaraboa.myapplication.models.UsersModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Interfaces {

    @GET("api/getCities")
    Call<List<CityModel>> getCities();

    @FormUrlEncoded
    @POST("api/getTown")
    Call<List<DistrictModel>> getTown(@Field("id") String id);


    @FormUrlEncoded
    @POST("api/setFavorite")
    Call<UsersModel> setFavorite(@Field("user") String user,@Field("product") String product);

    @FormUrlEncoded
    @POST("api/getFavorite")
    Call<List<Product>> getFavorite(@Field("user") String user);

    @FormUrlEncoded
    @POST("api/getDistrict")
    Call<List<DistrictModel>> getDistrict(@Field("id") String id,@Field("town") String town);




    @FormUrlEncoded
    @POST("api/loginUser")
    Call<UsersModel> loginUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/addUser")
    Call<UsersModel> addUser(@Field("name") String name, @Field("surname") String surname, @Field("password") String password, @Field("username") String username, @Field("mail") String mail);

    @FormUrlEncoded
    @POST("api/getProfile")
    Call<ProfileModel> getProfile(@Field("username") String username);

    @FormUrlEncoded
    @POST("api/searchLocation")
    Call<List<Product>> searchLocation(@Field("location") String location);

    @FormUrlEncoded
    @POST("api/searchCategory")
    Call<List<Product>> searchCategory(@Field("location") String location, @Field("category") String category);

    @FormUrlEncoded
    @POST("api/searchProduct")
    Call<List<Product>> searchProduct(@Field("location") String location, @Field("title") String title, @Field("category") String category);

    @FormUrlEncoded
    @POST("api/getProduct")
    Call<List<Product>> getProduct(@Field("product") String product, @Field("sell") Integer sell);

    @FormUrlEncoded
    @POST("api/updateProfile")
    Call<UsersModel> updateProfile(@Field("id") Integer id, @Field("name") String name,
                                   @Field("surname") String surname, @Field("biography") String biography,
                                   @Field("password") String password, @Field("username") String username,
                                   @Field("phone_number") String phone_number, @Field("mail") String mail);

    @Multipart
    @POST("api/setPicture")
    Call<ResponseBody> postImage(@Part MultipartBody.Part patch, @Part("id") Integer id);

    @Multipart
    @POST("api/addProduct")
    Call<UsersModel> addProduct(@Part MultipartBody.Part patch, @Part MultipartBody.Part patch_two, @Part MultipartBody.Part patch_three
            , @Part MultipartBody.Part patch_four, @Part MultipartBody.Part patch_five, @Part MultipartBody.Part patch_six
            , @Part MultipartBody.Part patch_seven, @Part MultipartBody.Part patch_eight
            , @Part MultipartBody.Part patch_nine, @Part MultipartBody.Part patch_ten, @Part("product_id") String product_id,
                                @Part("title") String title, @Part("price") String price,
                                @Part("statement") String statement,
                                @Part("location") String location, @Part("category") String category);


}
