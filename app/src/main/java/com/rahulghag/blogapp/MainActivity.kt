package com.rahulghag.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.domain.usecases.CheckUserLoginStatusUseCase
import com.rahulghag.blogapp.ui.navigation.SetupNavigation
import com.rahulghag.blogapp.ui.theme.BlogAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(mainViewModel = viewModel)
                }
            }
        }
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase
) : ViewModel() {
    private val _isUserLoggedIn: Channel<Boolean> = Channel()
    val isUserLoggedIn = _isUserLoggedIn.receiveAsFlow()

    init {
        viewModelScope.launch {
            _isUserLoggedIn.send(checkUserLoginStatusUseCase.invoke())
        }
    }
}