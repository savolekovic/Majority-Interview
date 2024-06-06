package com.vosaa.majoritytechassignment.presentation.country_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.shape.MaterialShapeDrawable
import com.vosaa.majoritytechassignment.R
import com.vosaa.majoritytechassignment.activites.MainActivity
import com.vosaa.majoritytechassignment.databinding.FragmentCountryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCountryDetailsBinding
    private val viewModel by viewModels<CountryDetailsViewModel>()

    private lateinit var countryName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_country_details,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        activity.setSupportActionBar(binding.countryToolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        countryName = CountryDetailsFragmentArgs.fromBundle(requireArguments()).countryName

        binding.countryToolbar.title = countryName
        binding.appBarLayout.statusBarForeground =
            MaterialShapeDrawable.createWithElevationOverlay(context)

        observeViewModel()
    }

    private fun observeViewModel() {

    }
}