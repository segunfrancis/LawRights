package com.segunfrancis.feature.my_rights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.data.remote.model.DataX
import com.segunfrancis.feature.my_rights.databinding.ItemRightsBinding

class LawRightsPagingAdapter :
    PagingDataAdapter<DataX, LawRightsPagingAdapter.MyRightsViewHolder>(RightsComparator) {

    class MyRightsViewHolder(private val binding: ItemRightsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataX?) = with(binding) {
            rightText.text = item?.title
            root.setOnClickListener {  }
        }
    }

    object RightsComparator : DiffUtil.ItemCallback<DataX>() {
        override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MyRightsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRightsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rights, parent, false)
        return MyRightsViewHolder(ItemRightsBinding.bind(view))
    }
}
