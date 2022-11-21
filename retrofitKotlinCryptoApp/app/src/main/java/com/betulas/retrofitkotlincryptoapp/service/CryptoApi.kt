package com.betulas.retrofitkotlincryptoapp.service

import android.database.Observable
import com.betulas.retrofitkotlincryptoapp.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {
//https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData():Call<List<CryptoModel>>


    ////We will do the asynchronous operations we do with Call. We don't blocking Main thread;
    //// What will be the result of this

}