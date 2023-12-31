package com.rahulghag.blogapp.ui.navigation

import androidx.annotation.StringRes
import com.rahulghag.blogapp.R

enum class Screen(@StringRes val title: Int) {
    Login(title = R.string.login),
    CreateAccount(title = R.string.create_account),
    Articles(title = R.string.articles)
}