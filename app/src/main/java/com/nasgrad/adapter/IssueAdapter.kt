package com.nasgrad.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.IssueResponse
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.issue_list_item.view.*

class IssueAdapter(private val context: Context, private val issues: List<Issue>, var listener: OnItemClickListener) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.issue_list_item, parent, false)
        return IssueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.setIssue(issues[position])
        holder.bindIssue(issues[position].id, listener)
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindIssue(itemId: String, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(itemId)
            }
        }

        fun setIssue(issue: Issue) {
            itemView.tvIssueTitle.text = issue.title
            itemView.tvIssueType.text = issue.issueType

//            itemView.tvCategory.text = issue.categories?.get(0)

//            if (issue.categories?.size!! > 1) {
//                itemView.tvSecondCategory.text = issue.categories[1]
//            } else {
//                itemView.tvSecondCategory.visibility = View.INVISIBLE
//            }
        }
    }
}