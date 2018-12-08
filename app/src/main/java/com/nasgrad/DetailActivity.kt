package com.nasgrad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.bumptech.glide.Glide
import com.nasgrad.MainActivity.Companion.ITEM_DESCRIPTION
import com.nasgrad.MainActivity.Companion.ITEM_TITLE
import com.nasgrad.MainActivity.Companion.ITEM_TYPE
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), OnClickListener {
    override fun onClick(view: View) {
        if (view.id == reportIssue.id) {
            openEmailClint()
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
        Glide.with(this).load("https://picsum.photos/100/100/?random").into(issuePicture)
        reportIssue.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
