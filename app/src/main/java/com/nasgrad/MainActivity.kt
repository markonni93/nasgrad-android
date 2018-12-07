package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    override fun onItemClicked(itemId: String) {
        Toast.makeText(this, "Item clicked $itemId ", Toast.LENGTH_SHORT).show()

        val detailsActivityIntent: Intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(ITEM_ID, itemId)
        }
        startActivity(detailsActivityIntent)
    }

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

        val issueAdapter = IssueAdapter(this, CaneModel.Cane, this)
//        Glide.with(this).load("https://picsum.photos/100/100/?random").into(ivIssueImage)
//        layoutManager.addView(ivIssueImage)
        rvIssueList.adapter = issueAdapter
    }

}
