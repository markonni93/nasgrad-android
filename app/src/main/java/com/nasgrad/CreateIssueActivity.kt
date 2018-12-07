package com.nasgrad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nasgrad.nasGradApp.R


class CreateIssueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_issue_details)

        setActionBarTitle(getString(R.string.issue_details_title))
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}

