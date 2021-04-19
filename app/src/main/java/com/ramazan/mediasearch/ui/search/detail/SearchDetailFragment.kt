package com.ramazan.mediasearch.ui.search.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.core.BaseFragment
import com.ramazan.mediasearch.databinding.SearchDetailFragmentBinding
import com.ramazan.mediasearch.databinding.SearchListFragmentBinding
import com.ramazan.mediasearch.ui.search.list.SearchListViewModel
import kotlin.reflect.KClass

class SearchDetailFragment() :
    BaseFragment<SearchDetailFragmentBinding, SearchDetailViewModel>(R.layout.search_detail_fragment) {
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }


    override fun viewModelClass() = SearchDetailViewModel::class

}