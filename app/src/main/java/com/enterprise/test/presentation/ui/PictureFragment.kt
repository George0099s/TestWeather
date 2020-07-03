package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import com.enterprise.test.R
import com.enterprise.test.presentation.adapter.PicAdapter
import com.enterprise.test.presentation.utils.InfiniteScrollListener
import com.enterprise.test.presentation.viewmodel.PictureViewModel
import com.enterprise.test.viewstate.PictureIntent
import com.enterprise.test.viewstate.PictureIntent.PictureScroll
import com.enterprise.test.viewstate.PictureViewState
import kotlinx.android.synthetic.main.picture_fragment.view.*


class PictureFragment : Fragment(), InfiniteScrollListener.OnLoadMoreListener {

    @BindView(R.id.picture_progress) lateinit var progress: ProgressBar

    companion object {
        fun newInstance() = PictureFragment()
    }

    private lateinit var viewModel: PictureViewModel
    lateinit var infiniteScrollListener: InfiniteScrollListener
    lateinit var adapter2: PicAdapter
    private val limit = 10
    private var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.picture_fragment, container, false)
        ButterKnife.bind(this, view)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val manager = LinearLayoutManager(context)
        infiniteScrollListener = InfiniteScrollListener(manager, this)
        infiniteScrollListener.setLoaded()
        adapter2 = PicAdapter(ArrayList(), context!!)

        view.picture_recycler.apply {
            layoutManager = manager
            addOnScrollListener(infiniteScrollListener)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = adapter2
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PictureViewModel::class.java)
        viewModel.pics.observe(viewLifecycleOwner, setPics())
        viewModel.events.observe(viewLifecycleOwner, intents())
        viewModel.events.value = PictureScroll(page, limit)
    }

    private fun intents() = Observer<PictureIntent>{
        when (it) {

            is PictureScroll -> {
                viewModel.loadPicture(page, limit)
            }
        }
    }



    private fun setPics() = Observer<PictureViewState>{
        when (it) {
            is PictureViewState.PictureLoadingState ->{
                adapter2.addNullData()
                page++
            }
            is PictureViewState.PictureLoadedState ->{
                adapter2.removeNull()
                adapter2.addData(it.pictures)
                infiniteScrollListener.setLoaded()
            }
            is PictureViewState.PictureNoItemsState ->{
                progress.visibility = View.VISIBLE
            }
            is PictureViewState.PictureErrorState ->{
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onLoadMore() {
        viewModel.events.value = PictureScroll(page, limit)
    }

}
