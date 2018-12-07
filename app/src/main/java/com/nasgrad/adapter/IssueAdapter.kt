package com.nasgrad.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nasgrad.Model
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.issue_list_item.view.*

class IssueAdapter(val context: Context, val issue: List<Model>) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.issue_list_item, parent, false)
        return IssueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issue.size
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val canes = issue[position]
        holder.setIssue(canes, "Kartografija")
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setIssue(issue: Model?, category: String) {
            itemView.tvIssueTitle.text = issue?.cane
            itemView.tvCategory.text = category
            if (itemView.tvSecondCategory.text.isEmpty()) {
                itemView.tvSecondCategory.visibility = View.INVISIBLE
            }
            Glide.with(context).load("https://picsum.photos/100/100/?random").into(itemView.ivIssueImage)
        }
    }
}