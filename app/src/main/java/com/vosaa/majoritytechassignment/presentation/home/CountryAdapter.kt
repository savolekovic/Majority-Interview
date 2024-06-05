package com.vosaa.majoritytechassignment.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vosaa.majoritytechassignment.R
import com.vosaa.majoritytechassignment.databinding.ItemCountryBinding
import com.vosaa.majoritytechassignment.domain.models.Country

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var fullCountryList: List<Country> = emptyList()

    class CountryViewHolder(private val itemBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(country: Country, onBlogCLickListener: ((String) -> Unit)?) {
            itemBinding.apply {

                Glide.with(root.context).load(country.flags.png).placeholder(R.drawable.ic_image)
                    .into(countryImage)
                countryName.text = country.name.official

                root.setOnClickListener {
                    onBlogCLickListener?.let { it(country.name.official) }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): CountryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val blogBinding = ItemCountryBinding.inflate(layoutInflater, parent, false)
                return CountryViewHolder(blogBinding)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(
            oldItem: Country,
            newItem: Country,
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Country,
            newItem: Country,
        ): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onCountryCLickListener)
    }

    private var onCountryCLickListener: ((String) -> Unit)? = null

    fun setOnCountryCLickListener(listener: (String) -> Unit) {
        onCountryCLickListener = listener
    }

    fun submitList(countries: List<Country>) {
        fullCountryList = countries
        differ.submitList(countries)
    }

    fun refreshList(){
        differ.submitList(fullCountryList)
    }

    fun searchCountries(query: String) {
        val filteredList = if (query.isEmpty()) fullCountryList
        else fullCountryList.filter {
            it.name.official.contains(query, ignoreCase = true)
        }
        differ.submitList(filteredList)
    }

}