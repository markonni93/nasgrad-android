package com.nasgrad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.constraint.solver.widgets.Helper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.nasgrad.MainActivity.Companion.ITEM_DESCRIPTION
import com.nasgrad.MainActivity.Companion.ITEM_IMAGE
import com.nasgrad.MainActivity.Companion.ITEM_TITLE
import com.nasgrad.MainActivity.Companion.ITEM_TYPE
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_add_image.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class DetailActivity : AppCompatActivity(), OnClickListener {

    override fun onClick(view: View) {
        if (view.id == reportIssue.id) {
            openEmailClint()
        } else if (view.id == shareBtn.id) {
            openTwitterApp()
        }
    }

    private fun openTwitterApp() {
//        val tweetIntent = Intent(Intent.ACTION_SEND)
//        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.")
//        tweetIntent.type = "text/plain"
//
//        val packManager = packageManager
//        val resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)
//
//        var resolved = false
//        for (resolveInfo in resolvedInfoList) {
//            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
//                tweetIntent.setClassName(
//                    resolveInfo.activityInfo.packageName,
//                    resolveInfo.activityInfo.name
//                )
//                resolved = true
//                break
//            }
//        }
//        if (resolved) {
//            startActivity(tweetIntent)
//        } else {
//            val i = Intent()
//            i.putExtra(Intent.EXTRA_TEXT, "Sonjaaaa")
//            i.action = Intent.ACTION_VIEW
//            i.data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode("Sonjaaaa"))
//            startActivity(i)
//            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show()
//        }

        val tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url=" + "https://www.google.com"
        val uri = Uri.parse(tweetUrl)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun urlEncode(string: String): String {
        return try {
            URLEncoder.encode(string, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.wtf("sonja", "UTF-8 should always be supported", e)
            ""
        }
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

        val itemTitle = intent.getStringExtra(ITEM_TITLE)
        titleDetailsLabel.text = itemTitle
        typeFromPredefinedList.text = "Tip problema: ${intent.getStringExtra(ITEM_TYPE)}"
        issueDetailDescTextView.text = intent.getStringExtra(ITEM_DESCRIPTION)
        issuePicture.setImageBitmap(com.nasgrad.utils.Helper.decodePicturePreview(intent.getStringExtra(ITEM_IMAGE)))
        reportIssue.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
