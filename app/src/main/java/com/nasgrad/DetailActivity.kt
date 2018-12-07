package com.nasgrad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nasgrad.MainActivity.Companion.ITEM_ID
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val itemID = intent.getStringExtra(ITEM_ID)
        titleDetailsLabel.text = itemID
        Glide.with(this).load("https://picsum.photos/100/100/?random").into(issuePicture)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
