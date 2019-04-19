package com.nasgrad.issue

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.esafirm.imagepicker.features.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nasgrad.adapter.TypesSpinnerAdapter
import com.nasgrad.api.model.IssueType
import com.nasgrad.api.model.Location
import com.nasgrad.nasGradApp.R
import com.nasgrad.utils.Helper
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_create_issue.*
import timber.log.Timber
import java.io.IOException


class CreateIssueFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener {

    private lateinit var images: List<com.esafirm.imagepicker.model.Image>

    private var map: GoogleMap? = null

    private lateinit var currentLocation: LatLng

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var location: Location


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_issue, container, false)
        (activity as CreateIssueActivity).setActionBarTitle(getString(R.string.create_issue_fragment_title))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibArrowRight.setOnClickListener(this)
        ibArrowLeft.setOnClickListener(this)

        initTypesSpinner()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = FusedLocationProviderClient(activity as CreateIssueActivity)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        openCameraButton.setOnClickListener(this)
        openGalleryButton.setOnClickListener(this)
        deletePicture.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            ibArrowLeft.id -> {
            }

            ibArrowRight.id -> {

                var issue = (activity as CreateIssueActivity).issue
                val bitmap = (imagePreview.drawable as BitmapDrawable).bitmap

                issue.picturePreview = Helper.encodePicturePreview(bitmap)
                issue.title = tvIssueTitle.text.toString()
                issue.description = etIssueDescription.text.toString()

                val categoryNames = listOf(
                    this.tvFirstCategory.text.toString(),
                    this.tvCategory2.text.toString(),
                    this.tvThirdCategory.text.toString()
                )

                //val type = spinnerTypes.selectedItem as IssueType
                //issue.issueType = type.name

                issue.location = location
                issue.address = tvAddress.text.toString()

                Timber.d("Issue details %s", issue.address)
                Timber.d("Issue details %s", issue.location)
                Timber.d("Issue details %s", issue.description)
                Timber.d("Issue details %s", issue.title)

                (activity as CreateIssueActivity).setFragment(R.id.mainContent, PreviewIssueFragment())

            }
            openCameraButton.id -> openCameraMode()
            openGalleryButton.id -> openGalleryMode()
            deletePicture.id -> deletePicture()

        }
    }

    private fun deletePicture() {
        imagePreview.setImageDrawable(activity?.getDrawable(R.drawable.ic_image))
        deletePicture.visibility = View.GONE
        openGalleryButton.visibility = View.VISIBLE
        openCameraButton.visibility = View.VISIBLE
    }

    private fun openCameraMode() {
        com.esafirm.imagepicker.features.ImagePicker.cameraOnly().start(this)
    }

    private fun openGalleryMode() {
        ImagePicker.create(this).theme(R.style.AppTheme).single().start()
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
        openGalleryButton.visibility = View.GONE
        openCameraButton.visibility = View.GONE
    }

    private fun initTypesSpinner() {
        val issueTypes = ArrayList(Helper.issueTypes.values)

        val adapter = TypesSpinnerAdapter((activity as CreateIssueActivity), R.layout.types_spinner_item, issueTypes)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)

        spinnerTypes.adapter = adapter
        spinnerTypes.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Timber.d("onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = parent?.getItemAtPosition(position) as IssueType
        Timber.d("onItemSelected $selectedType")

//        val categories = Helper.getCategoriesForType(selectedType)
//        when {
//            categories.size == 1 -> {
//                tvFirstCategory.visibility = View.VISIBLE
//                tvFirstCategory.text = categories[0].name
//                tvCategory2.visibility = View.GONE
//                tvThirdCategory.visibility = View.GONE
//            }
//            categories.size == 2 -> {
//                tvFirstCategory.visibility = View.VISIBLE
//                tvFirstCategory.text = categories[0].name
//                tvCategory2.visibility = View.VISIBLE
//                tvCategory2.text = categories[1].name
//                tvThirdCategory.visibility = View.GONE
//            }
//            categories.size >= 3 -> {
//                tvFirstCategory.visibility = View.VISIBLE
//                tvFirstCategory.text = categories[0].name
//                tvCategory2.visibility = View.VISIBLE
//                tvCategory2.text = categories[1].name
//                tvThirdCategory.visibility = View.VISIBLE
//                tvThirdCategory.text = categories[2].name
//            }
//            else -> {
//                tvFirstCategory.visibility = View.GONE
//                tvCategory2.visibility = View.GONE
//                tvThirdCategory.visibility = View.GONE
//            }
//        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        this.map?.setOnCameraIdleListener(this)

        getCurrentLocation()
        updateUI()
    }

    override fun onCameraIdle() {
        Timber.d("onCameraIdle")
        val latLng = map?.cameraPosition?.target!!
        location = Location(latLng.latitude.toString(), latLng.longitude.toString())

        val geocoder = Geocoder((activity as CreateIssueActivity))

        try {
            val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addressList != null && addressList.size > 0) {
                val locality = addressList[0].getAddressLine(0)
                val country = addressList[0].countryName
                if (!locality.isEmpty() && !country.isEmpty()) {
                    tvAddress.text = locality
                    Timber.d("$locality, $country")
                }
            } else {
                tvAddress.text = "Nepoznata lokacija"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getCurrentLocation() {
        try {
            if ((activity as CreateIssueActivity).permissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation!!
                locationResult.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(currentLocation.latitude, currentLocation.longitude), 15f
                            )
                        )

                    } else {
                        Timber.d("Location is null")
                    }
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e, "Exception")
        }
    }

    private fun updateUI() {
        if (map == null) {
            return
        }
        try {
            if ((activity as CreateIssueActivity).permissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Timber.e(e, "Exception")
        }

    }
}