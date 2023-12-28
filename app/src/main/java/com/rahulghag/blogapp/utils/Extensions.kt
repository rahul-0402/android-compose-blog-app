package com.rahulghag.blogapp.utils

fun String.isValidEmail(): Boolean {
    return this.contains('@')
}