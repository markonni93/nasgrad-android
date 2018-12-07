package com.nasgrad

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.fragment_add_image.*
import net.alhazmy13.mediapicker.Image.ImagePicker
import android.graphics.BitmapFactory
import android.R.attr.path
import android.support.v7.app.AppCompatActivity


class AddImageFragment : Fragment(), View.OnClickListener {

    var path: List<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_image, container, false)

        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.screen_title_add_image)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addImageButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            addImageButton.id -> pickImage()
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