package com.ramazan.mediasearch.ui.search.list

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ramazan.mediasearch.databinding.ViewholderSearchListRowBinding
import com.ramazan.mediasearch.network.responses.SearchResponse
import com.ramazan.mediasearch.utils.DateHelper

class SearchListAdapter(
    var list: List<SearchResponse.SearchResponseDetail>,
    val viewModel: SearchListViewModel
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding=ViewholderSearchListRowBinding.inflate(inflater,parent,false)
        return ViewHolder(binding,viewModel)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =holder.bind(list[position],position)

    inner class ViewHolder(
        val binding: ViewholderSearchListRowBinding,
        val viewModel: SearchListViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponse.SearchResponseDetail, position: Int) {
            binding.item = item
            binding.viewModel = viewModel
            binding.releaseDateTextView.text=DateHelper.formatServerUTCDateTimeLocal(item.releaseDate).toString()

            Glide
                .with(binding.root)
                .load(item.artworkUrl100)
                .apply(RequestOptions.centerInsideTransform())
                .into(binding.artWorkUrlImageView)

            binding.executePendingBindings()

        }
    }

}