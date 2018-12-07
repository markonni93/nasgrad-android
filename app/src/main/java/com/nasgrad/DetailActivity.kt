package com.nasgrad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.nasgrad.MainActivity.Companion.ITEM_ID
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
        val itemID = intent.getStringExtra(ITEM_ID)
        titleDetailsLabel.text = itemID
        reportIssue.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
