package com.enterprise.test.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.enterprise.test.R
import com.enterprise.test.data.App
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.PictureItem
import javax.inject.Inject


class PicAdapter(val pics: MutableList<PictureItem?>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_LOADING = 2
    private val glide = Glide.with(context)
    var list: MutableList<PictureItem?>? = arrayListOf()

    init{
        list = pics
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var root: View
        return if (viewType == VIEW_TYPE_ITEM) {
            root =
                LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
            PictureViewHolder(root)
        } else {
            root = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
            ProgressViewHolder(root)
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list!![position]
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            val holder = holder as PictureViewHolder
            glide.load(item!!.downloadUrl).into(holder.img)
            holder.author.text = item.author
        } else {
            val holder = holder as ProgressViewHolder
            holder.item.visibility = View.VISIBLE
        }
    }


    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.pict)
        val author = itemView.findViewById<TextView>(R.id.author)
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item = itemView.findViewById<ProgressBar>(R.id.item_progress)
    }


    override fun getItemViewType(position: Int): Int {
        if (list!![position] != null)
            return VIEW_TYPE_ITEM
        else
            return VIEW_TYPE_LOADING
    }

    fun addNullData() {
        list!!.add(null)
        notifyItemInserted(list!!.size - 1)
    }

    fun removeNull() {
        list!!.removeAt(list!!.size - 1)
        notifyItemRemoved(list!!.size)
    }

    fun addData(newList: List<PictureItem>) {
        list!!.addAll(newList)
        notifyDataSetChanged()
    }
}