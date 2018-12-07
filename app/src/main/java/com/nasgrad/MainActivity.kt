package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nasgrad.adapter.IssueAdapter
import com.nasgrad.adapter.OnItemClickListener
import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.IssueResponse
import com.nasgrad.nasGradApp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
        setupAdapter()
        mockedSetDataToAdapter()
    }

    private fun loadAllIssues(): Observable<Response<IssueResponse>> {
        val apiService = Retrofit.Builder()
            .baseUrl(this.resources.getString(R.string.base_api_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        return apiService.getAllIssues()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext {
                if (it.isSuccessful) {
                    setDataToAdapter(it?.body())
                } else {
                    Toast.makeText(this, "getAllIssues didn't return 200", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIssueList.layoutManager = layoutManager
    }

    private fun setDataToAdapter(issueResponse: IssueResponse?) {
        val issueAdapter = if (issueResponse != null) {
            IssueAdapter(this, issueResponse, this)
        } else {
            IssueAdapter(this, IssueResponse(mockListOfIssues()), this)
        }
        rvIssueList.adapter = issueAdapter
    }

    private fun mockedSetDataToAdapter() {
        rvIssueList.adapter = IssueAdapter(this, IssueResponse(mockListOfIssues()), this)
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
