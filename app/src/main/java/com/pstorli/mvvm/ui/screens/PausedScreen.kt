package com.pstorli.mvvm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pstorli.mvvm.model.ViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.pstorli.mvvm.R
import com.pstorli.mvvm.getString
import com.pstorli.mvvm.util.Consts.PAUSED_BTN_BACK_COLOR
import com.pstorli.mvvm.util.Consts.BTN_TEXT_COLOR

@Composable
fun PausedScreen (viewModel: ViewModel, modifier: Modifier = Modifier)
{
    // Make button appear center screen.
    Box (
        // Note, we set the back window color here.
        // This line both sets the background color, and listens for
        // any future changes in the background color in the viewModel
        modifier             = Modifier.fillMaxSize().background (viewModel.backgroundColor),
        contentAlignment     = Alignment.Center)
    {
        Button(
            // Toggle running state when clicked
            onClick = {
                viewModel.vh.toggleRunning()
            },

            // Handle button text and button backgriound color
            colors = ButtonDefaults.buttonColors (
                contentColor    = BTN_TEXT_COLOR,   // btn text
                containerColor  = PAUSED_BTN_BACK_COLOR    // btn background

            ),
            contentPadding  = ButtonDefaults.ButtonWithIconContentPadding)
        {
            Text (
                // From file strings.xml
                text     = viewModel.getString(R.string.start),
                color    = BTN_TEXT_COLOR,
                fontSize = 28.sp,
                modifier = modifier
            )
        }
    }
}
