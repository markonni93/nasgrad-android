package com.nasgrad.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nasgrad.api.model.Issue
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import com.nasgrad.utils.Helper.Companion.USER_ID_KEY
import com.nasgrad.utils.SharedPreferencesHelper
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
        val issue = issues[position]
        holder.bindIssue(issue.id, issue.title, issue.issueType, issue.description, issue.picturePreview, listener)
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindIssue(itemId: String, itemTitle: String?, itemType: String?, itemDesc: String?,itemImage:String?, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(itemId, itemTitle, itemType, itemDesc, itemImage)
            }
        }

        fun setIssue(issue: Issue) {
            itemView.tvIssueTitle.text = issue.title
            itemView.tvCounter.text = issue.submittedCount.toString()
            itemView.tvType.text = this@IssueAdapter.context.resources.getString(R.string.tip, Helper.getTypeName(issue.issueType))

            val ownerId = SharedPreferencesHelper(context).getStringValue(USER_ID_KEY, "0")

            if (issue.ownerId == ownerId) {
                itemView.list_item_container.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryLight))
            }
            
            if (issue.picturePreview != null) itemView.ivIssueImage.setImageBitmap(Helper.decodePicturePreview(issue.picturePreview))

            val list = issue.categories

            when {
                issue.categories?.size == 1 -> {
                    val issueCategory1 = Helper.getCategoryNameForCategoryId(list!![0])
                    itemView.tvCategory1.text = issueCategory1?.name
                    itemView.tvCategory1.setBackgroundColor(Color.parseColor(issueCategory1?.color))

                    itemView.tvCategory1.visibility = View.VISIBLE
                    itemView.tvCategory2.visibility = View.INVISIBLE
                    itemView.tvCategory3.visibility = View.INVISIBLE
                }
                issue.categories?.size == 2 -> {
                    val issueCategory1 = Helper.getCategoryNameForCategoryId(list!![0])
                    val issueCategory2 = Helper.getCategoryNameForCategoryId(list[1])
                    itemView.tvCategory1.text = issueCategory1?.name
                    itemView.tvCategory2.text = issueCategory2?.name
                    itemView.tvCategory1.setBackgroundColor(Color.parseColor(issueCategory1?.color))
                    itemView.tvCategory2.setBackgroundColor(Color.parseColor(issueCategory2?.color))

                    itemView.tvCategory1.visibility = View.VISIBLE
                    itemView.tvCategory2.visibility = View.VISIBLE
                    itemView.tvCategory3.visibility = View.INVISIBLE
                }
                issue.categories?.size == 3 -> {
                    val issueCategory1 = Helper.getCategoryNameForCategoryId(list!![0])
                    val issueCategory2 = Helper.getCategoryNameForCategoryId(list[1])
                    val issueCategory3 = Helper.getCategoryNameForCategoryId(list[2])
                    itemView.tvCategory1.text = issueCategory1?.name
                    itemView.tvCategory2.text = issueCategory2?.name
                    itemView.tvCategory3.text = issueCategory3?.name

                    itemView.tvCategory1.setBackgroundColor(Color.parseColor(issueCategory1?.color))
                    itemView.tvCategory2.setBackgroundColor(Color.parseColor(issueCategory2?.color))
                    itemView.tvCategory3.setBackgroundColor(Color.parseColor(issueCategory3?.color))

                    itemView.tvCategory1.visibility = View.VISIBLE
                    itemView.tvCategory2.visibility = View.VISIBLE
                    itemView.tvCategory3.visibility = View.VISIBLE
                }
            }
        }
    }
}