package com.andreikslpv.data

import com.andreikslpv.data.dto.cards.CardDetail
import com.andreikslpv.data.service.CardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Teemp {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.magicthegathering.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(CardService::class.java)

    fun temp(){
        service.getCard().enqueue(object : Callback<CardDetail> {
            override fun onResponse(call: Call<CardDetail>, response: Response<CardDetail>) {
                println("AAA response ${response.body()?.card?.name}")
            }

            override fun onFailure(call: Call<CardDetail>, t: Throwable) {
                println("AAA failure")
            }
        })
    }

//        val service = retrofit.create(CardsInSetService::class.java)
//
//        service.getCards().enqueue(object : Callback<ResultCards> {
//            override fun onResponse(call: Call<ResultCards>, response: Response<ResultCards>) {
//                println("AAA response ${response.body()?.cards?.size}" )
//            }
//
//            override fun onFailure(call: Call<ResultCards>, t: Throwable) {
//                println("AAA failure" )
//            }
//        })

//        val service = retrofit.create(SetsService::class.java)
//
//        service.getSets().enqueue(object : Callback<ResultSets> {
//            override fun onResponse(call: Call<ResultSets>, response: Response<ResultSets>) {
//                println("AAA response ${response.body()?.sets?.size}" )
//            }
//
//            override fun onFailure(call: Call<ResultSets>, t: Throwable) {
//                println("AAA failure" )
//            }
//        })

}