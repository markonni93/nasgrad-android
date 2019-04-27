package com.nasgrad.issue

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.nasgrad.R
import com.nasgrad.adapter.CategorySpinnerAdapter
import com.nasgrad.adapter.CityServiceSpinnerAdapter
import com.nasgrad.adapter.IssueTypeSpinnerAdapter
import com.nasgrad.api.model.CityService
import com.nasgrad.api.model.IssueType
import com.nasgrad.api.model.Location
import com.nasgrad.api.model.Type
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

    private var typesList: MutableMap<String, Type> = HashMap()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_issue, container, false)
        (activity as CreateIssueActivity).setActionBarTitle(getString(R.string.create_issue_fragment_title))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibArrowRight.setOnClickListener(this)
        ibArrowLeft.setOnClickListener(this)
        button_select_type.setOnClickListener(this)

        ibArrowLeft.visibility = View.VISIBLE
        //initCategorySpinner()
        initTypesSpinner()
        initCityCerviceSpinner()

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
                (activity as CreateIssueActivity).finish()
            }

            button_select_type.id -> {
                if (!typesList.isEmpty()) {
                    for (tip in typesList) {
                        Timber.d("%s", tip.value.name)
                    }
                } else {
                    displaySnackbar(view, "Izaberite službu da biste mogli da izaberete tip problema")
                }
            }

            ibArrowRight.id -> {

                val issue = (activity as CreateIssueActivity).issue

                if (imagePreview.drawable.constantState == resources.getDrawable(R.drawable.ic_image).constantState) {
                    Timber.e("No image loaded")
                } else {
                    val bitmap = (imagePreview.drawable as BitmapDrawable).bitmap
                    issue.picturePreview = Helper.encodePicturePreview(bitmap)
                }

                issue.title = tvIssueTitle.text.toString()
                issue.description = etIssueDescription.text.toString()

                val categoryNames = listOf(
                    this.tvFirstCategory.text.toString(),
                    this.tvCategory2.text.toString(),
                    this.tvThirdCategory.text.toString()
                )

                val type = spinnerCityService.selectedItem as IssueType
                //issue.issueType = category.name
                issue.issueType = type.name

                issue.location = location
                issue.address = tvAddress.text.toString()

                if (!issue.address.equals("nepoznata lokacija", true) &&
                    issue.location != null &&
                    !issue.title.isNullOrBlank() &&
                    issue.picturePreview != null
                ) {
                    (activity as CreateIssueActivity).setFragment(R.id.mainContent, PreviewIssueFragment())
                } else {
                    displaySnackbar(view, "Molimo Vas popunite sva polja pre prijavljivanja problema!")
                }
            }
            openCameraButton.id -> openCameraMode()
            openGalleryButton.id -> openGalleryMode()
            deletePicture.id -> deletePicture()
        }
    }

    private fun displaySnackbar(view: View, text: String) {
        val snackbar = Snackbar.make(
            view, text,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        snackbar.show()
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

    private fun initCategorySpinner() {
        val categoryTypes = ArrayList(Helper.issueCategories.values)

        val adapter =
            CategorySpinnerAdapter((activity as CreateIssueActivity), R.layout.types_spinner_item, categoryTypes)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)

        spinnerCityService.adapter = adapter
        spinnerCityService.onItemSelectedListener = this
    }

    private fun initTypesSpinner() {
        val issueTypes = ArrayList(Helper.issueTypes.values)

        val adapter =
            IssueTypeSpinnerAdapter((activity as CreateIssueActivity), R.layout.types_spinner_item, issueTypes)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)

        spinnerCityService.adapter = adapter
        spinnerCityService.onItemSelectedListener = this
    }

    private fun initCityCerviceSpinner() {
        val cityServices = ArrayList(Helper.cityServices.values)

        val adapter =
            CityServiceSpinnerAdapter((activity as CreateIssueActivity), R.layout.types_spinner_item, cityServices)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)
        spinnerCityService.adapter = adapter
        spinnerCityService.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Timber.d("onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) {
            val selectedCityService = parent?.getItemAtPosition(position) as CityService
            Timber.d("onItemSelected $selectedCityService")

            val cityServiceTypes = Helper.getCityServiceType()
            val issueTypes = Helper.getTypes()

            for (type in cityServiceTypes) {
                if (type.cityService.equals(selectedCityService.id)) {
                    Timber.d("Found city type service with same id as City Service %s", type.id)
                    for (tip in issueTypes) {
                        if (type.type.equals(tip.id)) {
                            typesList.put(tip.id, tip)
                            Timber.e("Matched types for selected city service are %s", tip.name)
                        }
                    }
                }
            }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

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

    override fun onPause() {
        stopLocationUpdates()
        super.onPause()
    }

    private fun stopLocationUpdates() {
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