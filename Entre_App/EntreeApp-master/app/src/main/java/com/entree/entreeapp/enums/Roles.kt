package com.entree.entreeapp.enums

enum class Roles {
    NOROLE, GUEST, ADMIN, TRAINER, EMPLOYEE;
    val value: Int
        get() = when (this) {
            NOROLE -> 0
            GUEST -> 1
            ADMIN -> 2
            TRAINER -> 3
            EMPLOYEE -> 4
        }
}