package com.mulganov.job.kotlin.list

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.mulganov.job.kotlin.Filtr
import com.mulganov.job.kotlin.R


public class FiltrAdapter(context: Context, list: ArrayList<FiltrInfo>) : BaseAdapter() {
    internal var sList = ArrayList<FiltrInfo>()
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
        val vh: FI
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.filtr, parent, false)
            vh = FI(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as FI
        }

        vh.label.text = sList[position].text
        vh.check.isChecked = Filtr.checkMap.get(sList[position].text) as Boolean

        vh.check.setOnClickListener {
            Filtr.checkMap.put(sList[position].text, vh.check.isChecked)
            println(vh.check.isChecked)
        }

        return view
    }
}

private class FI(row: View?) {
    public val label: TextView
    public val check: CheckBox

    init {
        this.label = row?.findViewById(R.id.ivText) as TextView
        this.check = row?.findViewById(R.id.ivCheck) as CheckBox
    }
}
