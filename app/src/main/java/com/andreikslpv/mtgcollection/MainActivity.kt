package com.andreikslpv.mtgcollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreikslpv.mtgcollection.dto.cards.CardDetail
import com.andreikslpv.mtgcollection.service.CardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CardService::class.java)

        service.getCard().enqueue(object : Callback<CardDetail> {
            override fun onResponse(call: Call<CardDetail>, response: Response<CardDetail>) {
                println("AAA response ${response.body()?.card?.name}" )
            }

            override fun onFailure(call: Call<CardDetail>, t: Throwable) {
                println("AAA failure" )
            }
        })

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
}