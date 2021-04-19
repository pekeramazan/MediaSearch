package com.ramazan.mediasearch.ui.search.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.core.BaseFragment
import com.ramazan.mediasearch.databinding.SearchDetailFragmentBinding
import com.ramazan.mediasearch.databinding.SearchListFragmentBinding
import com.ramazan.mediasearch.ext.observeEvent
import com.ramazan.mediasearch.ui.search.list.SearchListViewModel
import kotlin.reflect.KClass

class SearchDetailFragment() :
    BaseFragment<SearchDetailFragmentBinding, SearchDetailViewModel>(R.layout.search_detail_fragment) {
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }


    var artUrl100: String? = null
    var artistName: String? = null
    var collectionName: String? = null
    var longDescription: String? = null


    override fun viewModelClass() = SearchDetailViewModel::class
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artUrl100 = SearchDetailFragmentArgs.fromBundle(requireArguments()).artUrl100
        artistName = SearchDetailFragmentArgs.fromBundle(requireArguments()).artistName
        collectionName = SearchDetailFragmentArgs.fromBundle(requireArguments()).collectionName
        longDescription = SearchDetailFragmentArgs.fromBundle(requireArguments()).longDescription

        if (!artistName.isNullOrEmpty()) {
            viewBinding.artistNameTextView.text = artistName

        }
        if (!collectionName.isNullOrEmpty()) {
            viewBinding.collectionNameDetailTextView.text = collectionName

        }
        if (!longDescription.isNullOrEmpty()) {
            viewBinding.descriptionTextView.text = longDescription

        }
        if (!artUrl100.isNullOrEmpty()) {
            Glide
                .with(this)
                .load(artUrl100)
                .apply(RequestOptions.centerInsideTransform())
                .into(viewBinding.artWorkUrlDetailImageView)
        }





    }


}
