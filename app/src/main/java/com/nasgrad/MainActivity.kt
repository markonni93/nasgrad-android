package com.nasgrad

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.issue.CreateIssueActivity
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import com.nasgrad.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

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

        Timber.plant(Timber.DebugTree())

        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateIssueActivity::class.java))
        }
        setIssueListAdapter()

        // create unique user id which is used as issue owner
        createUserId()
    }

    private fun setIssueListAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager

        val issueAdapter = IssueAdapter(this, CaneModel.Cane, this)
        rvIssueList.adapter = issueAdapter
    }

    private fun createUserId() {
        val sharedPreferences = SharedPreferencesHelper(this)
        sharedPreferences.setStringValue(Helper.USER_ID_KEY, Helper.randomGUID())
    }

}
