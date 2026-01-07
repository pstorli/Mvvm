package com.pstorli.mvvm.repo

import androidx.compose.ui.graphics.Color
import com.pstorli.mvvm.logInfo
import com.pstorli.mvvm.model.ViewModel
import com.pstorli.mvvm.logVerbose
import com.pstorli.mvvm.util.Consts.randomColor

/** This class handles talking to remote data sources
to get data in the background, such as images from urls,
websites, vpn, or even local database , which is considered slow.

This class is currently designed to be called from class CoHelper.kt
so that it is never called directly from the ui thread.
If request is needed, add methods to CoHelper.kt to get to the
Repo.kt method you want to talk to.

The function getColorOfWord, normally does not need to be here,
it is not long running. But it have it here as an example
of how a longer running
routine should be handled.

    fun getColorOfWord (word: String): Color

 */
class Repo (var viewModel: ViewModel) {
    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Prefs Helper Methods
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a color.+
     */
    fun oneTimeDataFetchExample(): Color {
        val color = randomColor ()
        "Repo.oneTimeDataFetchExample returned color $color.".logInfo()
        return color
    }

    /**
     * This function is called repetedly, with delay, Consts.
     * the background task,
     * which keeps running, peroidically, between
     * Consts.MIN_DELAY and Consts.MAX_DELAY until
     * viewModel.state is PAUSED
     */
    fun executeBackgroundTask() {
        "Repo.executeBackgroundTask called.".logVerbose()

        val color = randomColor ()

        "Repo.executeBackgroundTask cnanged background color to $color".logInfo()

        // Set the new backgrouond color.
        viewModel.backgroundColor = color
    }
}