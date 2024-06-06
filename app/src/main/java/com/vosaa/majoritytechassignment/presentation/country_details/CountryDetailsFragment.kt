package com.vosaa.majoritytechassignment.presentation.country_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vosaa.majoritytechassignment.R
import com.vosaa.majoritytechassignment.activites.MainActivity
import com.vosaa.majoritytechassignment.databinding.FragmentCountryDetailsBinding
import com.vosaa.majoritytechassignment.util.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCountryDetailsBinding
    private val viewModel by viewModels<CountryDetailsViewModel>()

    private lateinit var countryName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_country_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        activity.setSupportActionBar(binding.detailsToolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        countryName = CountryDetailsFragmentArgs.fromBundle(requireArguments()).countryName
        binding.detailsToolbar.title = countryName

        setupErrorButton()
        observeViewModel()

        viewModel.getCountry(countryName)
    }

    private fun setupErrorButton() {
        binding.errorButton.setOnClickListener {
            viewModel.getCountry(countryName)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest {
                when (it) {
                    UiStates.LOADING -> {
                        toggleProgressBar(true)
                    }

                    UiStates.SUCCESS -> {
                        toggleProgressBar(false)
                    }

                    UiStates.NO_INTERNET_CONNECTION -> {
                        binding.detailsLoading.visibility = View.GONE
                        binding.detailsErrorContainer.visibility = View.VISIBLE
                        Toast.makeText(
                            binding.root.context,
                            getString(R.string.connection_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        binding.detailsLoading.visibility = View.GONE
                        binding.detailsErrorContainer.visibility = View.VISIBLE
                        Toast.makeText(
                            binding.root.context,
                            getString(R.string.unknown_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.countryDataFlow.collectLatest {
                binding.country = it
            }
        }
    }

    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.detailsLoading.visibility = View.VISIBLE
            binding.detailsLayout.visibility = View.GONE
            binding.detailsErrorContainer.visibility = View.GONE
        } else {
            binding.detailsLoading.visibility = View.GONE
            binding.detailsLayout.visibility = View.VISIBLE
            binding.detailsErrorContainer.visibility = View.GONE
        }
    }
}