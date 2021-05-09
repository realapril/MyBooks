package com.tistory.realapril.mybooks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tistory.realapril.mybooks.R
import com.tistory.realapril.mybooks.databinding.FragmentFavBookBinding
import com.tistory.realapril.mybooks.databinding.FragmentListBookBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavBookFragment : Fragment() {
    private val bViewModel by sharedViewModel<BookViewModel>()
    private lateinit var listAdapter: BookListAdapter
    private lateinit var viewDataBinding : FragmentFavBookBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentFavBookBinding.inflate(inflater, container, false).apply {
            viewmodel = bViewModel
            lifecycleOwner = this@FavBookFragment
        }
        listAdapter = BookListAdapter(bViewModel)
        viewDataBinding.rvConcerts.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Observe a list of books
        bViewModel.bookMarkList.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    }
}