package com.aevapay.android.task.ui.planetdetails

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aevapay.android.task.databinding.ItemPlanetAttributesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PlanetAttributesAdapter(
    private var planetList: ArrayList<Pair<String, Drawable>>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return planetList.size
    } // fun of getItemCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderPlanet(
            ItemPlanetAttributesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    } // fun of onCreateViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        planetList[position].let {
            (holder as ViewHolderPlanet).bind(context = context, planet = it)
        }
    } // fun of onBindViewHolder

    private class ViewHolderPlanet(val binding: ItemPlanetAttributesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, planet: Pair<String, Drawable>) {

            binding.txtRepoName.text = planet.first

            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().fitCenter())
                .load(planet.second)
                .into(binding.imgPlanet)
        } // fun of bind
    } // class of ViewHolder
}