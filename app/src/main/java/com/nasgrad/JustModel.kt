package com.nasgrad

data class Model(var id: String)

object CaneModel {
    var Cane = mutableListOf<Model>(
        Model("Cane"),
        Model("Sonja"),
        Model("Bojana"),
        Model("Strahinja"),
        Model("Dalibor"),
        Model("Lasko"),
        Model("Dorian")
    )
}