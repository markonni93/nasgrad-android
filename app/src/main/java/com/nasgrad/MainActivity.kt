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
import com.nasgrad.nasGradApp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

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

        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateIssueActivity::class.java))
        }

        setupAdapter()
        showIssues()
    }

    private fun showIssues() {
        disposable = client.getAllIssues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result != null) {
                        setDataToAdapter(result)
                    } else {
                        mockedSetDataToAdapter()
                    }
                },
                { error ->
                    Log.e("sonja", error.message)
                    mockedSetDataToAdapter()
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

    private fun mockedSetDataToAdapter() {
        rvIssueList.adapter = IssueAdapter(this, mockListOfIssues(), this)
    }

    private fun mockListOfIssues(): List<Issue> {
        return listOf(
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

    private fun mockListOfCategories(): List<String> {
        return listOf(
            "Kategorija1",
            "Kategorija2"
        )
    }
}
