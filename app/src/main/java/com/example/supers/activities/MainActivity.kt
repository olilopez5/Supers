package com.example.supers.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supers.R
import com.example.supers.SuperheroAdapter
import com.example.supers.api.SuperheroService
import com.example.supers.data.Superhero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SuperheroAdapter

    var superheroList: List<Superhero> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)

        adapter = SuperheroAdapter(superheroList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        getRetrofit()
        }

        fun getRetrofit(){

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/eae2834903ff6404d646eb85ad3a2cd5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(SuperheroService::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val result = service.findSupersByName("super")

                superheroList = result.results

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = superheroList
                    adapter.notifyDataSetChanged()
                }
            }
        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity_navigation, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("MENU", "He pulsado Enter")
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                ListSuperhero = Superhero.ListSuperhero.filter {
                    getString(it.name).contains(query, true)
                }
                adapter.updateItems(ListSuperhero)
                return false
            }
        })

        return true
    }
}


