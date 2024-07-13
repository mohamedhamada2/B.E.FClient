package com.mz.befclient.api;




import com.facebook.internal.AppCall;
import com.mz.befclient.about.AppModel;
import com.mz.befclient.basket.BasketModel;
import com.mz.befclient.basket.SuccessModel;
import com.mz.befclient.contactus.ContactusModel;
import com.mz.befclient.forgetpassword.NewPassword;
import com.mz.befclient.home.Category;
import com.mz.befclient.home.ImageModel;
import com.mz.befclient.home.OfferModel;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.Token;
import com.mz.befclient.notifications.NotificationAdapter;
import com.mz.befclient.notifications.NotificationModel;
import com.mz.befclient.orders.BillDetailsModel;
import com.mz.befclient.orders.Order;
import com.mz.befclient.products.ProductModel;
import com.mz.befclient.signup.City;
import com.mz.befclient.signup.Govern;

import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService{
    @GET("api_clients/Api_clients/get_categories")
    Call<Category> get_categories_home();
    @GET("api_clients/Api_clients/get_categories")
    Call<com.mz.befclient.categories.Category> get_categories();

    @FormUrlEncoded
    @POST("api_clients/Api_clients/register")
    Call<UserModel> signup(@Field("name")String name,@Field("govern_id_fk")String govern_id_fk,
                          @Field("city_id_fk")String city_id_fk,@Field("shop")String shop,
                          @Field("mob")String mob,@Field("adress")String adress,
                          @Field("latitude")String latitude,@Field("longitude")String longitude,
                          @Field("password")String password,@Field("deviceId")String deviceId);
    @GET("Api/get_governs")
    Call<List<Govern>> get_govern();
    @FormUrlEncoded
    @POST("Api/get_city")
    Call<List<City>> get_city(@Field("govern_id")String govern_id);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/login")
    Call<UserModel> login(@Field("mob")String name,@Field("password")String password,@Field("device_token")String device_token);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/get_products_category")
    Call<ProductModel> get_products(@Field("cat_id_fk")String cat_id_fk, @Field("page")Integer page);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/get_products_category")
    Call<ProductModel> get_products(@Field("page")Integer page);

    @GET("api_clients/Api_clients/get_products_category")
    Call<ProductModel> get_products_home();

    @GET("api_clients/Api_clients/get_offers")
    Call<OfferModel> get_offers();

    @FormUrlEncoded
    @POST("api_clients/Api_clients/offer_product")
    Call<ProductModel> get_products_offer(@Field("offer_id_fk")String offer_id_fk, @Field("page")Integer page);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/offer_product")
    Call<ProductModel> search_products_offer(@Field("offer_id_fk")String offer_id_fk,@Field("page")Integer page,@Field("search_word")String search_word);

    @POST("api_clients/Api_clients/save_basket")
    Call<SuccessModel> save_basket(@Body BasketModel basketModel);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/get_user_orders")
    Call<Order> get_user_orders(@Field("user_id")String user_id,@Field("status")String status,@Field("page")Integer page);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/get_fatora_detail")
    Call<BillDetailsModel> get_bill_details2(@Field("fatora_id")String fatora_id);

    @GET("api_clients/Api_clients/info")
    Call<AppModel> get_about_app();

    @FormUrlEncoded
    @POST("api_clients/Api_clients/search_product")
    Call<ProductModel> search_product(@Field("search_word")String search_word,
                                      @Field("category_id_fk")String category_id_fk,
                                      @Field("page")Integer page);
    @FormUrlEncoded
    @POST("api_clients/Api_clients/add_contact")
    Call<ContactusModel> contact_us(@Field("user_id_fk")String user_id_fk,
                                    @Field("name")String name,
                                    @Field("phone")String phone,
                                    @Field("msg_type")String msg_type,
                                    @Field("content")String content);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/get_notification")
    Call<NotificationModel> get_user_notifications(@Field("user_id_fk")String user_id_fk,
                                                   @Field("page")Integer page,
                                                   @Field("type")String type);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/update_token")
    Call<Token> update_user_token(@Field("user_id_fk")String user_id_fk,
                                  @Field("token")String token);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/update_profile")
    Call<UserModel> update_profile(@Field("name")String name,@Field("govern_id_fk")String govern_id_fk,
                           @Field("city_id_fk")String city_id_fk,@Field("shop")String shop,
                           @Field("mob")String mob,@Field("adress")String adress,
                           @Field("latitude")String latitude,@Field("longitude")String longitude,
                           @Field("row_id")String row_id);

    @GET("api_clients/Api_clients/get_slideimages")
    Call<ImageModel> get_images();

    @FormUrlEncoded
    @POST("api_clients/Api_clients/update_password")
    Call<UserModel> edit_password(@Field("old_password")String old_password,
                                  @Field("new_password")String new_password,
                                  @Field("user_id_fk")String user_id_fk);
    @FormUrlEncoded
    @POST("api_clients/Api_clients/check_mob")
    Call<NewPassword> check_phone(@Field("mob")String mob);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/forget_password")
    Call<NewPassword> new_password(@Field("mob")String mob,@Field("password")String password);

    @FormUrlEncoded
    @POST("api_clients/Api_clients/update_version")
    Call<ContactusModel> updateVersion(@Field("versionName")String versionName);

    /*@FormUrlEncoded
    @POST("api_clients/Api_trader/get_product_details")
    Call<ProductDetailsModel> get_product_datails(@Field("product_id")String product_id);*/
}

