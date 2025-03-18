package com.example.supers.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supers.R
import com.example.supers.SuperheroAdapter
import com.example.supers.api.SuperheroService
import com.example.supers.data.Superhero
import com.example.supers.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    //lateinit var recyclerView: RecyclerView
    lateinit var adapter: SuperheroAdapter
    lateinit var binding: ActivityMainBinding

    var superheroList: List<Superhero> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //binding.recyclerView
        //recyclerView = findViewById(R.id.recyclerView)

        adapter = SuperheroAdapter(superheroList) { position -> // renombrar it
            val superhero = superheroList[position]

            //Toast.makeText(this, superhero.name, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SUPERHERO_ID", superhero.id)
            startActivity(intent)

        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchSuperheroesByName("a")

        supportActionBar?.title = "League Of Heroes"
        supportActionBar?.setHomeButtonEnabled(true)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity_navigation, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // para llamadas a internet lo busca completo, no letra por letra
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperheroesByName(query)
                //getRetrofit(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        return true
    }
    // Solo para construir el objeto SuperheroService
    fun getRetrofit() : SuperheroService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/eae2834903ff6404d646eb85ad3a2cd5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(SuperheroService::class.java)
    }


    private fun searchSuperheroesByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.findSupersByName(query)

                superheroList = result.results

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = superheroList
                    adapter.notifyDataSetChanged()
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}