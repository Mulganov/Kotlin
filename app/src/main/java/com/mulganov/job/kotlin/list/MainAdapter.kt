package com.mulganov.job.kotlin.list

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mulganov.job.kotlin.R


public class MainAdaptor(context: Context, list: ArrayList<Product>) : BaseAdapter() {
    internal var sList = ArrayList<Product>()
    private val mInflator: LayoutInflater


    init {
        this.mInflator = LayoutInflater.from(context)
        for (product in list){

            sList.add(product)
        }

        println(list.size)

    }

    override fun getCount(): Int {
        return sList.size
    }

    override fun getItem(position: Int): Any {
        return sList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = sList[position].name
        vh.img.setImageBitmap(sList[position].bitmap)
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val label: TextView
    public val img: ImageView

    init {
        this.label = row?.findViewById(R.id.ivText) as TextView
        this.img = row?.findViewById(R.id.ivImage) as ImageView
    }
}
