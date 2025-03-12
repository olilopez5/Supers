package com.example.supers

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supers.api.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getRetrofit()
        }

        fun getRetrofit(){

            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/eae2834903ff6404d646eb85ad3a2cd5")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var service = retrofit.create(SuperheroService::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val result = service.findSupersByName("super")
                for (superhero in result)
                Log.i("API", "${result.id} -> ${result.name}")
            }


        }

    }

