package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerImageView = findViewById<ImageView>(R.id.headerImageView)
        val fullName = findViewById<TextView>(R.id.fullName)
        val gender = findViewById<ImageView>(R.id.gender)
        val origin = findViewById<TextView>(R.id.origin)
        val species = findViewById<TextView>(R.id.species)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAppCompatActivity: RickAndMortyService =
            retrofit.create(RickAndMortyService::class.java)

        rickAppCompatActivity.getCharacterById(68).enqueue(object : Callback<GetCharacterByIdResponse> {
            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                Log.i("MainActivity", response.toString())
                if (!response.isSuccessful){
                    Toast.makeText(this@MainActivity,"no network call",Toast.LENGTH_LONG).show()
                    return
                }
                val body = response.body()!!
                val name = body.name

                fullName.text = name
            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.i("MainActivity", t.message ?: "null message")
            }

        })
    }
}