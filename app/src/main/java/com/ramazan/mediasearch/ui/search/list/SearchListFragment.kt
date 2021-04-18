package com.ramazan.mediasearch.ui.search.list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.core.BaseFragment
import com.ramazan.mediasearch.databinding.SearchListFragmentBinding
import com.ramazan.mediasearch.ext.observe
import com.ramazan.mediasearch.ext.observeEvent
import com.ramazan.mediasearch.network.responses.SearchResponse
import okhttp3.internal.notifyAll
import java.util.HashMap
import kotlin.reflect.KClass

class SearchListFragment :
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
            SearchListViewEvent.Error -> {
                Toast.makeText(
                    context,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()

            }
            is SearchListViewEvent.ShowInputError -> {
                when (searchListViewEvent.input) {
                    SearchListInputEnums.SEARCH_TEXT_EMPTY -> {


                    }
                    SearchListInputEnums.SEARCH_TEXT_CHR -> {


                    }
                }

            }

            is SearchListViewEvent.Filter ->{
                when(searchListViewEvent.input) {
                    SearchCategoryEnums.Categories.MOVIE -> {
                        viewBinding.buttonMovie.isEnabled=false
                        viewBinding.buttonAll.isEnabled=true
                        viewBinding.buttonBooks.isEnabled=true
                        viewBinding.buttonMusic.isEnabled=true
                        viewBinding.buttonApps.isEnabled=true

                        onDataChange( viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.MOVIE.categories })

                    }
                    SearchCategoryEnums.Categories.MUSIC -> {
                        viewBinding.buttonAll.isEnabled=true
                        viewBinding.buttonMovie.isEnabled=true
                        viewBinding.buttonBooks.isEnabled=true
                        viewBinding.buttonMusic.isEnabled=false
                        viewBinding.buttonApps.isEnabled=true
                        onDataChange( viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.MUSIC.categories })

                    }
                    SearchCategoryEnums.Categories.BOOK -> {
                        viewBinding.buttonAll.isEnabled=true
                        viewBinding.buttonMovie.isEnabled=true
                        viewBinding.buttonBooks.isEnabled=false
                        viewBinding.buttonMusic.isEnabled=true
                        viewBinding.buttonApps.isEnabled=true
                        onDataChange( viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.BOOK.categories })

                    }
                    SearchCategoryEnums.Categories.APPS -> {
                        viewBinding.buttonAll.isEnabled=true
                        viewBinding.buttonMovie.isEnabled=true
                        viewBinding.buttonBooks.isEnabled=true
                        viewBinding.buttonMusic.isEnabled=true
                        viewBinding.buttonApps.isEnabled=false
                        onDataChange( viewModel.searchResponse.value!!.filter { s -> s.kind == SearchCategoryEnums.Categories.APPS.categories })

                    }
                    SearchCategoryEnums.Categories.ALL -> {
                        viewBinding.buttonAll.isEnabled=false
                        viewBinding.buttonMovie.isEnabled=true
                        viewBinding.buttonBooks.isEnabled=true
                        viewBinding.buttonMusic.isEnabled=true
                        viewBinding.buttonApps.isEnabled=true
                        onDataChange( viewModel.searchResponse.value!!)

                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>2){
                    viewModel.setSearchText(newText)
                }
                return false

            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewBinding.buttonMovie.isEnabled=true
                viewBinding.buttonBooks.isEnabled=true
                viewBinding.buttonMusic.isEnabled=true
                viewBinding.buttonApps.isEnabled=true
                viewBinding.buttonAll.isEnabled=false
                viewModel.onSearch()
                return false
            }

        })
    }


}
