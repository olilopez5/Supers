package com.example.supers.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supers.R
import com.example.supers.api.SuperheroService
import com.example.supers.data.Superhero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("SUPERHERO_ID")!!

        getSuperById(id)
    }

    fun loadData(){
        TODO("Informacion del superheroe")
    }


}
fun getRetrofit(): SuperheroService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.superheroapi.com/api.php/7252591128153666/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(SuperheroService::class.java)
}


fun getSuperById(id: String) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val service = getRetrofit()
            superhero = service.findSuperheroById(id)

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
}