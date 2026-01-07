package com.pstorli.mvvm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pstorli.mvvm.R
import com.pstorli.mvvm.getString
import com.pstorli.mvvm.model.ViewModel
import com.pstorli.mvvm.ui.core.OutlinedButton
import com.pstorli.mvvm.util.Consts
import com.pstorli.mvvm.util.Consts.BTN_TEXT_COLOR

// import com.pstorli.mvvm.ui.core.OutlinedButton
@Composable
fun RunningScreen (viewModel: ViewModel, modifier: Modifier = Modifier)
{
    // Make button appear center screen.
    Box (
        // Note, we set the back window color here.
        // This line both sets the background color, and listens for
        // any future changes in the background color in the viewModel
        modifier             = Modifier.fillMaxSize().background (viewModel.backgroundColor),
        contentAlignment     = Alignment.Center)
    {
        // A custom button I made, in ui.core
        // that adds an colored, outline border
        OutlinedButton (
            name        = viewModel.getString(R.string.stop),
            backColor   = Consts.RUNNING_BTN_BACK_COLOR,
            borderColor = Color.Red,
            textColor   = BTN_TEXT_COLOR,

            // Toggle running state when clicked
            onClick     = {
                // Tell the view model to toggle the running state.
                viewModel.vh.toggleRunning()
            })
    }
}
