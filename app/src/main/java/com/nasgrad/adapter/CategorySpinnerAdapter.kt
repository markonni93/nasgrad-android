package com.nasgrad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nasgrad.R
import com.nasgrad.api.model.IssueCategory
import kotlinx.android.synthetic.main.types_spinner_item.view.*

class CategorySpinnerAdapter(context: Context, resource: Int, objects: ArrayList<IssueCategory>) :
    ArrayAdapter<IssueCategory>(context, resource, objects) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        val viewHolder: ViewHolder

        if (row != null) {
            viewHolder = row.tag as ViewHolder
        } else {
            row = layoutInflater.inflate(R.layout.types_spinner_item, parent, false)
            viewHolder = ViewHolder(row)
            row?.tag = viewHolder
        }

        val type = getItem(position)
        viewHolder.categoryName?.text = type?.name

        //row?.setPadding(0, row.paddingTop, 0, row.paddingBottom);
        return row!!
    }

    inner class ViewHolder(row: View?) {
        val categoryName: TextView? = row?.tvCategoryName
    }
}