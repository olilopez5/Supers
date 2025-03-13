package com.example.supers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.supers.data.Superhero
import com.squareup.picasso.Picasso

class SuperheroAdapter(var items: List<Superhero>) : Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperheroViewHolder(view)
    }

    override fun getItemCount(): Int =  items.size  //celdas que pintar , tantas con superheroes haya

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {

        val superhero = items[position]
        holder.render(superhero)

    }
    fun updateItems(items: List<Superhero>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(view: View) : ViewHolder(view){

    val nameTView: TextView = view.findViewById(R.id.nameTView)
    val heroImageView: ImageView = view.findViewById(R.id.heroImageView)

    fun render(superhero: Superhero) {
        nameTView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(heroImageView);
    }
}