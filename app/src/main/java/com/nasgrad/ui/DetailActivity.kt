package com.nasgrad.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.nasgrad.ApiClient
import com.nasgrad.R
import com.nasgrad.ui.MainActivity.Companion.ITEM_ID
import com.nasgrad.api.model.Issue
import com.nasgrad.utils.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class DetailActivity : AppCompatActivity(), OnClickListener {

    lateinit var displayedIssue: Issue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detailActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        detailActivityToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))

        val itemItemId = intent.getStringExtra(ITEM_ID)
        showDetailIssue(itemItemId)

        reportIssue.setOnClickListener(this)
        share_btn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            reportIssue.id -> openEmailClint()
            share_btn.id -> shareTwitter(
                resources.getString(
                    R.string.tweetText,
                    displayedIssue.title,
                    displayedIssue.id
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private var map: GoogleMap? = null

    private fun shareTwitter(message: String) {
        val tweetIntent = Intent(Intent.ACTION_SEND)
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message)
        tweetIntent.type = "text/plain"

        val packManager = packageManager
        val resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)

        var resolved = false
        for (resolveInfo in resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
                resolved = true
                break
            }
        }
        if (resolved) {
            startActivity(tweetIntent)
        } else {
            val i = Intent()
            i.putExtra(Intent.EXTRA_TEXT, message)
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message))
            startActivity(i)
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show()
        }
    }

    private fun urlEncode(s: String): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Timber.e("UTF-8 should always be supported $e")
            ""
        }
    }

    private var disposable: Disposable? = null

    private val client by lazy {
        ApiClient.create()
    }

    private fun openEmailClint() {
        val recipient = resources.getString(R.string.email)
        val cc = "nas-grad-app@gmail.com"
        val subject = Uri.encode(String.format(getString(R.string.email_subject), displayedIssue.title))
        val body = Uri.encode(String.format(getString(R.string.email_body), displayedIssue.title, displayedIssue.id))
        val email = String.format(getString(R.string.email_template), recipient, cc, subject, body)

        // send email
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(email)
        this.startActivity(emailIntent)
    }

    private fun showDetailIssue(itemId: String) {
        disposable = client.getIssueItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if (result != null) {
                    displayedIssue = result
                    setUIDetailsScreen(result)
                }
            }
    }

    private fun setUIDetailsScreen(issue: Issue?) {
        titleDetailsLabel.text = issue?.title
        if (issue?.picturePreview != null) {
            issuePicture.setImageBitmap(Helper.decodePicturePreview(issue.picturePreview!!))
        }
        issueDetailDescTextView.text = issue?.description
        typeFromPredefinedList.text = Helper.getTypeName(issue?.issueType)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
