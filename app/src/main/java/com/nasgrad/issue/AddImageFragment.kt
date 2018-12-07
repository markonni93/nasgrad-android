package com.nasgrad.issue

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_add_image.*
import net.alhazmy13.mediapicker.Image.ImagePicker


class AddImageFragment : Fragment(), View.OnClickListener {

    var path: List<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_image, container, false)
        (activity as CreateIssueActivity).setActionBarTitle(getString(R.string.issue_picture_title))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addImageButton.setOnClickListener(this)
        ibArrowRight.setOnClickListener(this)

        ibArrowLeft.visibility = View.GONE
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), 1)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            addImageButton.id -> pickImage()
            ibArrowRight.id -> {
                // update issue
                // (activity as CreateIssueActivity).issue?.photo =
                val fragment = IssueDetailsFragment()
                (activity as CreateIssueActivity).setFragment(R.id.mainContent, fragment)
            }
        }
    }

    private fun pickImage() {
        ImagePicker.Builder(this.activity)
            .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
            .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
            .directory(ImagePicker.Directory.DEFAULT)
            .extension(ImagePicker.Extension.PNG)
            .scale(300, 300)
            .allowMultipleImages(false)
            .enableDebuggingMode(true)
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("sonja", "onActivityResult() called with: requestCode = [$requestCode], resultCode = [$resultCode], data = [$data]"
        )
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            path = data!!.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)
            Log.d("sonja", "onActivityResult: ")
            loadImage()
        }
    }

    private fun loadImage() {
        Log.d("sonja", "loadImage: " + path?.size)

        if (path != null) {
            imagePath.text = path?.get(0)
            addImageButton.setImageBitmap(BitmapFactory.decodeFile(path?.get(0)))
        }
    }

}