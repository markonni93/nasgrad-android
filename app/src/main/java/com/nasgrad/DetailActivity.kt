package com.nasgrad

import android.content.Intent
<<<<<<< HEAD
=======
import android.content.pm.PackageManager
>>>>>>> 07ea5d23f74b33b25514e40e37c11a4f6fc5ab13
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
<<<<<<< HEAD
=======
import android.widget.Toast
>>>>>>> 07ea5d23f74b33b25514e40e37c11a4f6fc5ab13
import com.google.android.gms.maps.GoogleMap
import com.nasgrad.MainActivity.Companion.ITEM_ID
import com.nasgrad.api.model.Issue
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
<<<<<<< HEAD
=======
import timber.log.Timber
>>>>>>> 07ea5d23f74b33b25514e40e37c11a4f6fc5ab13
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class DetailActivity : AppCompatActivity(), OnClickListener {

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            reportIssue.id -> openEmailClint()
            shareBtn.id -> shareTwitter("Text to tweet")
        }
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
        val intent = Intent(Intent.ACTION_SENDTO)
        val data = Uri.parse("mailto:${resources.getString(R.string.email)}?subject=prijava problema&body=OpisProblem")
        intent.data = data
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val itemItemId = intent.getStringExtra(ITEM_ID)
        showDetailIssue(itemItemId)

        reportIssue.setOnClickListener(this)
        shareBtn.setOnClickListener(this)
    }

    private fun showDetailIssue(itemId: String) {
        disposable = client.getIssueItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if (result != null) setUIDetailsScreen(result)
            }
    }

    private fun setUIDetailsScreen(issue: Issue?) {
        titleDetailsLabel.text = issue?.title
        if (issue?.picturePreview != null) issuePicture.setImageBitmap(Helper.decodePicturePreview(issue.picturePreview!!))
        issueDetailDescTextView.text = issue?.description
        typeFromPredefinedList.text = Helper.getTypeName(issue?.issueType)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
