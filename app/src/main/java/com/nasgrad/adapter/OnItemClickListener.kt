package com.nasgrad.adapter

interface OnItemClickListener {

    fun onItemClicked(itemID:String, itemTitle: String?, itemType: String?, itemDesc: String?)
}