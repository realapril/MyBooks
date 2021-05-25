package com.tistory.realapril.mybooks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tistory.realapril.mybooks.databinding.ItemBookListBinding
import com.tistory.realapril.mybooks.entity.Item

class BookListAdapter(val clickListener: ClickListener) : ListAdapter<Item, BookListAdapter.ViewHolder>(MyPageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Item, clickListener: ClickListener) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class ClickListener(val clickListener: (item: Item) -> Unit) {
        fun onClick(item: Item) = clickListener(item)
    }

}

class MyPageDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}