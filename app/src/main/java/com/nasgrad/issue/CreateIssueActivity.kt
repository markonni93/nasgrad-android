package com.nasgrad.issue

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.nasgrad.api.model.Issue
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import com.nasgrad.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_create_issue.*


class CreateIssueActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    lateinit var issue: Issue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_issue)

        setSupportActionBar(toolbar)
        enableHomeButton(true)
        setActionBarTitle(getString(R.string.issue_picture_title))

        setFragment(R.id.mainContent, AddImageFragment())

        val sharedPreferences = SharedPreferencesHelper(this)
        val issueId = Helper.randomGUID()
        val ownerId = sharedPreferences.getStringValue(Helper.USER_ID_KEY,"")
        issue = Issue(issueId, ownerId, null, null, null, null, null,null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setFragment(layoutId: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(layoutId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun openPreviousFragment() {
        fragmentManager.popBackStack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun enableHomeButton(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }

}

