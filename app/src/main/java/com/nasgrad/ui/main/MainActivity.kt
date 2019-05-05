package com.nasgrad.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.nasgrad.ApiClient
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.Location
import com.nasgrad.issue.CreateIssueActivity
import com.nasgrad.R
import com.nasgrad.ui.DetailActivity
import com.nasgrad.ui.base.BaseActivity
import com.nasgrad.utils.Helper
import com.nasgrad.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(), OnItemClickListener {

    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    private val client by lazy {
        ApiClient.create()
    }

    private var disposable: Disposable? = null

    override fun onItemClicked(
        itemId: String,
        itemTitle: String?,
        itemType: String?,
        itemDecs: String?,
        imageItem: String?) {

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

        createUserId()
        setupAdapter()
        showIssues()
    }

    private fun showIssues() {
        disposable = client.getAllIssues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                if (result != null)
                    setDataToAdapter(result)
            }, {error -> Timber.e(error)})
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

    private fun mockedSetDataToAdapter() {
        rvIssueList.adapter = IssueAdapter(this, mockListOfIssues(), this)
    }

    private fun mockListOfIssues(): List<Issue> {
        return listOf(
            Issue(
                "001",
                "123",
                "naslov",
                "opis",
                "tip",
                mockListOfCategories(),
                Location("23423", "234234"),
                "kreiran",
                resources.getString(R.string.imageBase64),
                3,
                "Pavla Papa"
            ),
            Issue(
                "002",
                "123",
                "naslov",
                "opis",
                "tip",
                mockListOfCategories(),
                Location("234234", "23452345"),
                "kreiran",
                resources.getString(R.string.imageBase64),
                2,
                "Bulevar Oslobodjenja"
            )
        )
    }

    private fun mockListOfCategories(): List<String> {
        return listOf(
            "Kategorija1",
            "Kategorija2"
        )
    }
}
