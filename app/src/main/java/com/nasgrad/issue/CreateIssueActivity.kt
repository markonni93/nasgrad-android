package com.nasgrad.issue

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.nasgrad.api.model.Issue
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import com.nasgrad.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_create_issue.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import timber.log.Timber

class CreateIssueActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val fragmentManager = supportFragmentManager

    lateinit var issue: Issue

    var permissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_issue)

        setSupportActionBar(toolbar)
        enableHomeButton(true)
        setActionBarTitle(getString(R.string.issue_picture_title))
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))

        setFragment(R.id.mainContent, CreateIssueFragment())

        // create initial issue
        val sharedPreferences = SharedPreferencesHelper(this)
        val issueId = Helper.randomGUID()
        val ownerId = sharedPreferences.getStringValue(Helper.USER_ID_KEY, "")
        issue = Issue(issueId, ownerId, null, null, null, null, null, null, null, null, null)

        // request location permission
        hasPermissions()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun hasPermissions() {
        permissionGranted = EasyPermissions.hasPermissions(
            this, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (!permissionGranted) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(
                    this, 101, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                    .setRationale("Potrebno je da omogucite pristup lokaciji kako bi...")
                    .setPositiveButtonText("U redu")
                    .setNegativeButtonText("Odustani")
                    .build()
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Timber.d("Permission denied")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Timber.d("Permission granted")
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

