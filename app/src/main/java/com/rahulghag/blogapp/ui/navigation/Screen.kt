package com.rahulghag.blogapp.ui.navigation

import androidx.annotation.StringRes
import com.rahulghag.blogapp.R

enum class Screen(@StringRes val title: Int) {
    Login(title = R.string.login),
    Registration(title = R.string.registration),
}