package com.example.supers.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supers.R
import com.example.supers.api.SuperheroService
import com.example.supers.data.Superhero
import com.example.supers.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("SUPERHERO_ID")!!

        getSuperById(id)

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_bio -> {
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.VISIBLE
                }
                R.id.action_appearance -> {
                    binding.statsContent.root.visibility = View.GONE
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.VISIBLE
                }
                R.id.action_stats -> {
                    binding.biographyContent.root.visibility = View.GONE
                    binding.appearanceContent.root.visibility = View.GONE
                    binding.statsContent.root.visibility = View.VISIBLE
                }
            }
            true
        }

        binding.navigationBar.selectedItemId = R.id.action_bio
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun loadData() {
        Picasso.get().load(superhero.image.url).into(binding.heroImageView)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = superhero.name
        //supportActionBar?.subtitle = superhero.biography.fullName

        // Biography
        binding.biographyContent.publisherTextView.text = superhero.biography.publisher
        binding.biographyContent.fullNameTextView.text = superhero.biography.fullName
        binding.biographyContent.placeOfBirthTextView.text = superhero.biography.placeBirth
        binding.biographyContent.alignmentTextView.text = superhero.biography.alignment.uppercase()
        binding.biographyContent.baseTextView.text = superhero.work.base
        binding.biographyContent.occupationTextView.text = superhero.work.occupation
        binding.biographyContent.alterEgosTextView.text = superhero.biography.alterEgos


        // Appearance
        binding.appearanceContent.raceTextView.text = superhero.appearance.race
        binding.appearanceContent.genderTextView.text = superhero.appearance.gender
        binding.appearanceContent.weightTextView.text = superhero.appearance.getWeightKg()
        binding.appearanceContent.heightTextView.text = superhero.appearance.getHeightCm()

        // Stats
        with(superhero.powerstats){
            binding.statsContent.intelligenceTextView.text = "${intelligence.toIntOrNull() ?: 0}"
            binding.statsContent.strengthTextView.text = "${strength.toIntOrNull() ?: 0}"
            binding.statsContent.speedTextView.text = "${speed.toIntOrNull() ?: 0}"
            binding.statsContent.durabilityTextView.text = "${durability.toIntOrNull() ?: 0}"
            binding.statsContent.powerTextView.text = "${power.toIntOrNull() ?: 0}"
            binding.statsContent.combatTextView.text = "${combat.toIntOrNull() ?: 0}"


            binding.statsContent.intelligenceProgressBar.progress = intelligence.toIntOrNull() ?: 0
            binding.statsContent.strengthProgressBar.progress = strength.toIntOrNull() ?: 0
            binding.statsContent.speedProgressBar.progress = speed.toIntOrNull() ?: 0
            binding.statsContent.durabilityProgressBar.progress = durability.toIntOrNull() ?: 0
            binding.statsContent.powerProgressBar.progress = power.toIntOrNull() ?: 0
            binding.statsContent.combatProgressBar.progress = combat.toIntOrNull() ?: 0
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




