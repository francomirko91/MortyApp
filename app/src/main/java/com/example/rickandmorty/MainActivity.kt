package com.example.rickandmorty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val headerImageView = findViewById<ImageView>(R.id.headerImageView)
        val fullName = findViewById<TextView>(R.id.fullName)
        val gender = findViewById<ImageView>(R.id.gender)
        val origin = findViewById<TextView>(R.id.origin2)
        val species = findViewById<TextView>(R.id.species2)
        val alive = findViewById<TextView>(R.id.alive)



        viewModel.refreshCharacter(54)
        viewModel._characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

            fullName.text = response.name
            alive.text = response.status
            species.text = response.species
            origin.text = response.origin.name


            if (response.gender.equals("male", true)) {
                gender.setImageResource(R.drawable.ic_male_24)
            } else {
                gender.setImageResource(R.drawable.ic_female_24)
            }

            Picasso.get().load(response.image).into(headerImageView);


        }


        /*NetworkLayer.apiClient.getCharacterById(54)
        rickAppCompatActivity.getCharacterById(34).enqueue(object : Callback<GetCharacterByIdResponse> {

            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                Log.i("MainActivity", response.toString())
                if (!response.isSuccessful){
                    Toast.makeText(this@MainActivity,"no network call",Toast.LENGTH_LONG).show()
                    return
                }
                val body = response.body()!!
                fullName.text = body.name
                alive.text = body.status
                species.text = body.species
                origin.text = body.origin.name

                Picasso.get().load(body.image).into(headerImageView);

                if (body.gender.equals("male",true)) {
                    gender.setImageResource(R.drawable.ic_male_24)
                }
                else {
                    gender.setImageResource(R.drawable.ic_female_24)
                }


            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.i("MainActivity", t.message ?: "null message")
            }

        })*/
    }
}