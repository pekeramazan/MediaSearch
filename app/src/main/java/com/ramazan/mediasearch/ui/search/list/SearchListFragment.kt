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
import java.util.HashMap
import kotlin.reflect.KClass

class SearchListFragment :
    BaseFragment<SearchListFragmentBinding, SearchListViewModel>(R.layout.search_list_fragment) {

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        initSearchListAdapter()
        observe(viewModel.searchResponse, ::updateAdapter)

    }
    private val searchListAdapter: SearchListAdapter by lazy {
        SearchListAdapter(
            ArrayList<SearchResponse.SearchResponseDetail>(),
            viewModel
        )
    }
    override fun viewModelClass() = SearchListViewModel::class

    private fun initSearchListAdapter() {
        viewBinding.searchListRcyclerView.adapter = searchListAdapter
        viewBinding.searchListRcyclerView.setHasFixedSize(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(viewModel.event, ::onViewEvent)

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
                viewModel.onSearch()
                return false
            }

        })
    }


    private fun updateAdapter(items: ArrayList<SearchResponse.SearchResponseDetail>) {
        searchListAdapter.updateAdapter(items)
    }

}
