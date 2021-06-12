package com.ysfsym.topratedmoviesandtvshows.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static ApiService service;
    public static ApiService getInstance() {
        if(service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(ApiService.class);
        }

        return service;
    }
}
