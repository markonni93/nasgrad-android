package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nasgrad.adapter.IssueAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.issue_list_item.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateIssueActivity::class.java))
        }
        setIssueListAdapter()
    }

    fun setIssueListAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager

        val issueAdapter = IssueAdapter(this, CaneModel.Cane)
//        Glide.with(this).load("https://picsum.photos/100/100/?random").into(ivIssueImage)
//        layoutManager.addView(ivIssueImage)
        rvIssueList.adapter = issueAdapter


    }
}
