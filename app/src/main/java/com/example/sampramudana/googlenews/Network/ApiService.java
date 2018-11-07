package com.example.sampramudana.googlenews.Network;

import com.example.sampramudana.googlenews.Model.ResponseGoogle;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("top-headlines?sources=google-news&apiKey=0a3624cc60104378b8ac6bb15d4dcd78")
    Call<ResponseGoogle> readNewsApi();
}
