package com.pstorli.mvvm.model

import com.pstorli.mvvm.domain.State.PAUSED
import com.pstorli.mvvm.domain.State.RUNNING

/**
 * This class is used for logic that is short lived,
 * as we are on the ui thread here. The reason is that
 * I like to keep the viewModel as solely a data repository,
 * and as little logic, functions, as possible.
 */

class VMHelper (var viewModel: ViewModel)
{
    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // Used to run coroutines in the background.
    var ch   = Back (viewModel)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Functions
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Toggle state
     */
    fun toggleRunning() {
        // toggle runninmg state
        if (RUNNING == viewModel.state) {
            viewModel.state = PAUSED
        }
        else {
            viewModel.state = RUNNING
        }

        // If new state is running, start the background task,
        // which keeps running, peroidically, between
        // Consts.MIN_DELAY and Consts.MAX_DELAY until
        // viewModel.state is PAUSED
        if (RUNNING == viewModel.state) {
            // Make sure that back thread state.
            // This task throws out random colors at random times.
            // In this app it is simulatinmg outside data trickling in.
            ch.backgroundTaskCoroutine()
        }
        else {
            // get paused background color.
            ch.dataFecthCoRoutine ()
        }
    }
}