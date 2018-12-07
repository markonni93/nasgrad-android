package com.nasgrad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.nasgrad.adapter.TypesSpinnerAdapter
import com.nasgrad.model.Category
import com.nasgrad.model.Type
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_issue_details.*


class CreateIssueActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_issue_details)

        setActionBarTitle(getString(R.string.issue_details_title))
        initTypesSpinner()
        setPageIndicator(2)
    }

    private fun initTypesSpinner() {
        val firstCategories = arrayListOf<Category>()
        firstCategories.add(Category("1", "Kategorija 1", "Opis 1", "email1@gmail.com"))
        firstCategories.add(Category("2", "Kategorija 2", "Opis 2", "email2@gmail.com"))

        val secondCategories = arrayListOf<Category>()
        secondCategories.add(Category("3", "Kategorija 3", "Opis 3", "email3@gmail.com"))
        secondCategories.add(Category("4", "Kategorija 4", "Opis 4", "email4@gmail.com"))

        val firstType = Type("1", "Tip 1", "Opis 1", firstCategories)
        val secondType = Type("2", "Tip 2", "Opis 2", secondCategories)

        val types = arrayListOf<Type>()
        types.add(firstType)
        types.add(secondType)

        val adapter = TypesSpinnerAdapter(this, R.layout.types_spinner_item, types)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)
        spinnerTypes.adapter = adapter
        spinnerTypes.onItemSelectedListener = this

    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun setPageIndicator(pageId: Int) {
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), pageId)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = parent?.getItemAtPosition(position) as Type
        Log.d("CreateIssueActivity", "Selected type " + selectedType.name)

        val categories = selectedType.categories
        if (categories != null && categories.isNotEmpty()) {

            if (categories[0] != null) {
                tvFirstCategory.visibility = View.VISIBLE
                tvFirstCategory.text = categories[0]?.name
            } else {
                tvFirstCategory.visibility = View.GONE
            }

            if (categories[1] != null) {
                tvFirstCategory.visibility = View.VISIBLE
                tvFirstCategory.text = categories[1]?.name
            } else {
                tvFirstCategory.visibility = View.GONE
            }
        }
    }
}
