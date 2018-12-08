package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.IssueResponse
import com.nasgrad.nasGradApp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

    override fun onItemClicked(itemId: String, itemTitle: String?, itemType: String?, itemDecs: String?) {
        Toast.makeText(this, "Item clicked $itemId ", Toast.LENGTH_SHORT).show()

        val detailsActivityIntent: Intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(ITEM_ID, itemId)
            putExtra(ITEM_TITLE, itemTitle)
            putExtra(ITEM_TYPE, itemType)
            putExtra(ITEM_DESCRIPTION, itemDecs)
        }
        startActivity(detailsActivityIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateIssueActivity::class.java))
        }

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
