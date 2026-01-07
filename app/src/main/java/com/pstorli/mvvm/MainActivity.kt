package com.pstorli.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pstorli.mvvm.model.ViewModel
import com.pstorli.mvvm.ui.screens.PausedScreen
import com.pstorli.mvvm.ui.theme.MvvmTheme
import com.pstorli.mvvm.domain.State.PAUSED
import com.pstorli.mvvm.domain.State.RUNNING
import com.pstorli.mvvm.ui.screens.RunningScreen

/**
This class is the applications main activity.

This is the only place we instantiate the ViewModel.kt class.
It is passed to composeables on their constructor.

The function, onCreate, in MainActivity.kt

override fun onCreate(savedInstanceState: Bundle?) {}

is called when program starts up and is on the ui thread, so no long running operations here, just call setContent to set the composeable to view,
In this case, PausedScreen.kt
 */
class MainActivity : ComponentActivity() {
    val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        "MainActivity.onCreate Started!".logInfo()

        enableEdgeToEdge()
        
        setContent {
            MvvmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Show correct screen for game state.
                    when (viewModel.state) {
                        PAUSED  ->
                            PausedScreen (
                                viewModel =  viewModel,
                                modifier  = Modifier.padding(innerPadding)
                            )
                        RUNNING ->
                            RunningScreen (
                                viewModel = viewModel,
                                modifier  = Modifier.padding(innerPadding)
                            )
                        // No 'else' needed if all cases are covered!
                    }
                }
            }
        }
    }
}