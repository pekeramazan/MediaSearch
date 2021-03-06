package com.ramazan.mediasearch.ui.search.list

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.core.BaseFragment
import com.ramazan.mediasearch.databinding.SearchListFragmentBinding
import com.ramazan.mediasearch.ext.observe
import com.ramazan.mediasearch.ext.observeEvent
import com.ramazan.mediasearch.network.responses.SearchResponse

class SearchListFragment() :
    BaseFragment<SearchListFragmentBinding, SearchListViewModel>(R.layout.search_list_fragment) {
    private lateinit var adapter: SearchListAdapter


    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observe(viewModel.searchResponse, ::onDataChange)
    }

    override fun viewModelClass() = SearchListViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(viewModel.event, ::onViewEvent)

    }

    private fun onDataChange(item: List<SearchResponse.SearchResponseDetail>) {
        adapter = SearchListAdapter(item, viewModel)
        viewBinding.searchListRcyclerView.adapter = adapter

    }

    private fun onViewEvent(searchListViewEvent: SearchListViewEvent) {
        when (searchListViewEvent) {

             is SearchListViewEvent.NavigateDetail -> {
                navigateToDetail(searchListViewEvent.artUrl100,searchListViewEvent.artistName,searchListViewEvent.collectionName,searchListViewEvent.longDescription)
            }
            SearchListViewEvent.Error -> {
                Toast.makeText(
                    context,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()

            }

            is SearchListViewEvent.Filter -> {
                when (searchListViewEvent.input) {
                    SearchCategoryEnums.Categories.MOVIE -> {
                        viewBinding.buttonMovie.isEnabled = false
                        viewBinding.buttonAll.isEnabled = true
                        viewBinding.buttonBooks.isEnabled = true
                        viewBinding.buttonMusic.isEnabled = true
                        viewBinding.buttonApps.isEnabled = true

                        onDataChange(viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.MOVIE.categories })

                    }
                    SearchCategoryEnums.Categories.MUSIC -> {
                        viewBinding.buttonAll.isEnabled = true
                        viewBinding.buttonMovie.isEnabled = true
                        viewBinding.buttonBooks.isEnabled = true
                        viewBinding.buttonMusic.isEnabled = false
                        viewBinding.buttonApps.isEnabled = true
                        onDataChange(viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.MUSIC.categories })

                    }
                    SearchCategoryEnums.Categories.BOOK -> {
                        viewBinding.buttonAll.isEnabled = true
                        viewBinding.buttonMovie.isEnabled = true
                        viewBinding.buttonBooks.isEnabled = false
                        viewBinding.buttonMusic.isEnabled = true
                        viewBinding.buttonApps.isEnabled = true
                        onDataChange(viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.BOOK.categories })

                    }
                    SearchCategoryEnums.Categories.APPS -> {
                        viewBinding.buttonAll.isEnabled = true
                        viewBinding.buttonMovie.isEnabled = true
                        viewBinding.buttonBooks.isEnabled = true
                        viewBinding.buttonMusic.isEnabled = true
                        viewBinding.buttonApps.isEnabled = false
                        onDataChange(viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.APPS.categories })

                    }
                    SearchCategoryEnums.Categories.ALL -> {
                        viewBinding.buttonAll.isEnabled = false
                        viewBinding.buttonMovie.isEnabled = true
                        viewBinding.buttonBooks.isEnabled = true
                        viewBinding.buttonMusic.isEnabled = true
                        viewBinding.buttonApps.isEnabled = true
                        onDataChange(viewModel.searchResponse.value!!)

                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 3) {
                    viewModel.setSearchText(newText)
                }
                return false

            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewBinding.buttonMovie.isEnabled = true
                viewBinding.buttonBooks.isEnabled = true
                viewBinding.buttonMusic.isEnabled = true
                viewBinding.buttonApps.isEnabled = true
                viewBinding.buttonAll.isEnabled = false
                viewModel.onSearch()
                return false
            }

        })
    }

    private fun navigateToDetail(
        artUrl100: String?,
        artistName: String?,
        collectionName: String?,
        longDescription: String?
    ) {
        val action = SearchListFragmentDirections.actionSearchListFragmentToSearchDetailFragment()
        action.artistName = artistName
        action.collectionName = collectionName
        action.longDescription = longDescription
        action.artUrl100 = artUrl100

        this.findNavController().navigate(action)


    }
}

