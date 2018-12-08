package com.nasgrad

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.api.model.Issue
import com.nasgrad.utils.SharedPreferencesHelper

import com.nasgrad.api.model.IssueResponse
import com.nasgrad.issue.CreateIssueActivity
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnItemClickListener {

    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    val client by lazy {
        ApiClient.create()
    }

    var disposable: Disposable? = null

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
        // create unique user id which is used as issue owner
        createUserId()

        showIssues()
        setupAdapter()
//        mockedSetDataToAdapter()
    }

    private fun showIssues() {
        disposable = client.getAllIssues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
//                {result -> Log.e("sonja", " ${result}")},
                {result -> setDataToAdapter(result)},
                {error -> Log.e("sonja", error.message)}
            )
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager
    }

    private fun setDataToAdapter(issues: List<Issue>) {
        val issueAdapter = if (issues != null) {
            IssueAdapter(this, issues, this)
        } else {
            IssueAdapter(this, mockListOfIssues(), this)
        }
        rvIssueList.adapter = issueAdapter
    }

    private fun createUserId() {
        val sharedPreferences = SharedPreferencesHelper(this)
        sharedPreferences.setStringValue(Helper.USER_ID_KEY, Helper.randomGUID())
    }

    private fun mockedSetDataToAdapter() {
        rvIssueList.adapter = IssueAdapter(this, mockListOfIssues(), this)
    }

    private fun mockListOfIssues(): ArrayList<Issue> {
        return arrayListOf(
            Issue(
                "001", "123", "naslov", "opis",
                "tip", mockListOfCategories(), "kreiran"
            ),
            Issue(
                "002", "123", "naslov", "opis",
                "tip", mockListOfCategories(), "kreiran"
            )
        )
    }

    private fun mockListOfCategories(): ArrayList<String> {
        return arrayListOf(
            "Kategorija1",
            "Kategorija2"
        )
    }
}
