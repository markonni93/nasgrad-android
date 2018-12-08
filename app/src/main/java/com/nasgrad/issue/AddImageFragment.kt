package com.nasgrad.issue

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esafirm.imagepicker.features.ImagePicker
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_add_image.*


class AddImageFragment : Fragment(), View.OnClickListener {

    private lateinit var images: List<com.esafirm.imagepicker.model.Image>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_image, container, false)
        (activity as CreateIssueActivity).setActionBarTitle(getString(R.string.issue_picture_title))
        (activity as AppCompatActivity).supportActionBar!!.hide()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibArrowRight.setOnClickListener(this)

        ibArrowLeft.visibility = View.GONE
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), 1)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        openCameraButton.setOnClickListener(this)
        openGallaryButton.setOnClickListener(this)
        deletePicture.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            ibArrowRight.id -> {
                // update issue
                // (activity as CreateIssueActivity).issue?.photo =
                val fragment = IssueDetailsFragment()
                (activity as CreateIssueActivity).setFragment(R.id.mainContent, fragment)
            }
            openCameraButton.id -> openCameraMode()
            openGallaryButton.id -> openGalleryMode()
            deletePicture.id -> deletePicture()

        }
    }

    private fun deletePicture() {
        imagePreview.setImageDrawable(activity?.getDrawable(R.drawable.ic_image))
        deletePicture.visibility = View.GONE
        openGallaryButton.visibility = View.VISIBLE
        openCameraButton.visibility = View.VISIBLE
    }

    private fun openCameraMode() {
        com.esafirm.imagepicker.features.ImagePicker.cameraOnly().start(this)
    }

    private fun openGalleryMode() {
        ImagePicker.create(this).start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = ImagePicker.getImages(data) as ArrayList<com.esafirm.imagepicker.model.Image>
            loadImage()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadImage() {
        imagePreview.setImageBitmap(BitmapFactory.decodeFile(images[0].path))
        deletePicture.visibility = View.VISIBLE
        openGallaryButton.visibility = View.GONE
        openCameraButton.visibility = View.GONE
    }

}