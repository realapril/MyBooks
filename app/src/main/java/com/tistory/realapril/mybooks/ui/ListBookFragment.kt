package com.tistory.realapril.mybooks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tistory.realapril.mybooks.databinding.FragmentListBookBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListBookFragment : Fragment() {
    private val bViewModel by sharedViewModel<BookViewModel>()
    private lateinit var listAdapter: BookListAdapter
    private lateinit var viewDataBinding : FragmentListBookBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentListBookBinding.inflate(inflater, container, false).apply {
            viewmodel = bViewModel
            lifecycleOwner = this@ListBookFragment
        }
        listAdapter = BookListAdapter(BookListAdapter.ClickListener { item ->
            //Toast.makeText(context, "${item.id}", Toast.LENGTH_LONG).show()
            bViewModel.saveBookMark(item)
        })
        // remember last scroll position
        listAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        viewDataBinding.rvBooks.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
        // Observe a list of books
        bViewModel.bookList.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })

        // Listener of scrollview, To paging the list when the list touches the floor
        viewDataBinding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val positionOfScroll = viewDataBinding.scrollView.scrollY
            val last_position = viewDataBinding.scrollView.getChildAt(0).bottom - viewDataBinding.scrollView.height

            if(positionOfScroll == last_position) {
                // Call the next part of the list
                // I do not handle a paging of the list here :)
            }
        }
        return viewDataBinding.root
    }

}