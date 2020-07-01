package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.test.R
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.PictureItem
import com.enterprise.test.presentation.adapter.PicAdapter
import com.enterprise.test.presentation.utils.InfiniteScrollListener
import com.enterprise.test.presentation.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.picture_fragment.view.*


class PictureFragment : Fragment(), InfiniteScrollListener.OnLoadMoreListener {

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
        viewModel.loadPicture(page, limit)
    }

    private fun setPics() = Observer<Picture>{
        if (adapter2.list!!.size > 0)
        adapter2.removeNull()
        adapter2.addData(it)
        infiniteScrollListener.setLoaded()
        Toast.makeText(context, adapter2.itemCount.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onLoadMore() {
        adapter2.addNullData()
        page++
        viewModel.loadPicture(page, limit)
    }

}
