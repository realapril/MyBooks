package com.tistory.realapril.mybooks.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tistory.realapril.mybooks.databinding.ItemBookListBinding
import com.tistory.realapril.mybooks.entity.Item

class BookListAdapter(private val viewModel: BookViewModel) : ListAdapter<Item, BookListAdapter.ViewHolder>(MyPageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ItemBookListBinding, private val viewModel: BookViewModel) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }

        /**
         * When click a performance item from performance list,
         * Go to a detail page of item
         * */
        override fun onClick(v: View?) {
            binding.item?.let {
                viewModel.saveBookMark(it)
            }
        }


        companion object {
            fun from(parent: ViewGroup, viewModel: BookViewModel) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, viewModel)
            }
        }
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