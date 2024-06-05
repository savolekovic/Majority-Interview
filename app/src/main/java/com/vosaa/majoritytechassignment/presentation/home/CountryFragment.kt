package com.vosaa.majoritytechassignment.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.vosaa.majoritytechassignment.R
import com.vosaa.majoritytechassignment.activites.MainActivity
import com.vosaa.majoritytechassignment.databinding.FragmentCountryBinding
import com.vosaa.majoritytechassignment.util.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private val viewModel by viewModels<CountryViewModel>()

    private lateinit var searchView: SearchView

    @Inject
    lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_country, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        activity.setSupportActionBar(binding.homeToolbar)


        setupMenu()
        setupRecycler()
        observeViewModel()
        viewModel.getAllCountries()

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
                        binding.homeLoading.visibility = View.GONE
                        Toast.makeText(
                            binding.root.context,
                            getString(R.string.connection_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        binding.homeLoading.visibility = View.GONE
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
            viewModel.countriesDataFlow.collectLatest {
                //Submit list to adapter
                countryAdapter.submitList(it)
                if (it.isEmpty()) binding.homeErrorContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecycler() {
        countryAdapter.setOnCountryCLickListener {
            findNavController().navigate(
                CountryFragmentDirections.actionCountryFragmentToCountryDetailsFragment(it)
            )
        }
        binding.homeRecycler.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = countryAdapter
        }
    }

    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.homeLoading.visibility = View.VISIBLE
            binding.homeRecycler.visibility = View.GONE
            binding.homeErrorContainer.visibility = View.GONE
        } else {
            binding.homeLoading.visibility = View.GONE
            binding.homeRecycler.visibility = View.VISIBLE
            binding.homeErrorContainer.visibility = View.GONE
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_search) searchView =
                    menuItem.actionView as SearchView
                searchView.setQueryHint("Search countries...")
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        countryAdapter.searchCountries(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        return false
                    }
                })
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}