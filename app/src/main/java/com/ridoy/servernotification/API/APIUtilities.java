package com.ridoy.servernotification.API;

import com.ridoy.servernotification.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtilities {

    public static Retrofit retrofit=null;

   public static APIInterface getClient(){
       if (retrofit==null){
           retrofit = new Retrofit.Builder()
                   .baseUrl(Constants.BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit.create(APIInterface.class);
   }
}
