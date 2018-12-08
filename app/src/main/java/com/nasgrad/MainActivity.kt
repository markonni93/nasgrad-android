package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.api.model.Issue
import com.nasgrad.issue.CreateIssueActivity
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import com.nasgrad.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnItemClickListener {

    companion object {
        const val ITEM_ID = "ITEM_ID"
        const val ITEM_TITLE = "ITEM_TITLE"
        const val ITEM_IMAGE = "ITEM_IMAGE"
        const val ITEM_TYPE = "ITEM_TYPE"
        const val ITEM_DESCRIPTION = "ITEM_DESCRIPTION"
    }

    val client by lazy {
        ApiClient.create()
    }

    var disposable: Disposable? = null

    override fun onItemClicked(itemId: String, itemTitle: String?, itemType: String?, itemDecs: String?, imageItem:String?) {
        Toast.makeText(this, "Item clicked $itemId ", Toast.LENGTH_SHORT).show()
        val detailsActivityIntent: Intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(ITEM_ID, itemId)
            putExtra(ITEM_TITLE, itemTitle)
            putExtra(ITEM_TYPE, itemType)
            putExtra(ITEM_DESCRIPTION, itemDecs)
            putExtra(ITEM_IMAGE,imageItem)
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

        createUserId()
        setupAdapter()
        showIssues()
    }

    private fun showIssues() {
        disposable = client.getAllIssues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result != null)
                        setDataToAdapter(result)
                }
            )
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager
    }

    private fun setDataToAdapter(issues: List<Issue>) {
        rvIssueList.adapter = IssueAdapter(this, issues, this)
    }

    private fun createUserId() {
        val sharedPreferences = SharedPreferencesHelper(this)
        sharedPreferences.setStringValue(Helper.USER_ID_KEY, Helper.randomGUID())
    }
}
