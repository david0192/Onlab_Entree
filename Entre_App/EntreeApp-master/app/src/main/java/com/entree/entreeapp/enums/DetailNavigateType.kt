package com.entree.entreeapp.enums

enum class DetailNavigateType {
    NEW, VIEW, EDIT;
    val value: Int
        get() = when (this) {
            NEW -> 0
            VIEW -> 1
            EDIT -> 2
        }
}