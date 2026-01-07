package com.pstorli.mvvm.model

import androidx.lifecycle.viewModelScope
import com.pstorli.mvvm.logError
import com.pstorli.mvvm.logInfo
import com.pstorli.mvvm.logVerbose
import com.pstorli.mvvm.repo.Repo
import com.pstorli.mvvm.util.Consts
import com.pstorli.mvvm.util.Consts.MAX_DELAY
import com.pstorli.mvvm.util.Consts.MIN_DELAY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

import com.pstorli.mvvm.domain.State.PAUSED
import com.pstorli.mvvm.domain.State.RUNNING

/**
 * This class is used to spin off co-routines which call
 * long state functions in the Repo.kt class
 * NOTE: The repo class is private, we don't want it being
 * used by anyone but this class, which knows how to launch
 * coroutines.
 */

class Back (var viewModel: ViewModel)
{
    // Our continual back task.
    private var bj: Job?    = null

    // Used to request long state data
    // Repository stuff, do long state things in the back.
    // Thereason that it is private, i that people requesting
    // access to the repo, should go through this class,
    // so that the request is done on a backgroung thread,
    // because long state operatio9ns should
    // NOT BE DONE ON THE UI THREAD
    private var repo         = Repo (viewModel)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // These routines cause coroutines to be run in back
    // so ui thread is not used for 'long state' operations.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Use this as an exampole of asking the repo for something
     * in the back, so we don't hang ui thread.
     *
     * Get a color on the back thread.
     *
     * To simulate how to have a long state
     * routine not hang up the ui threead.
     */
    @Suppress("unused")
    fun dataFecthCoRoutine () {
        "Back.dataFecthCoRoutine() started ... ".logVerbose()

        viewModel.viewModelScope.launch {
            // Get the game, the whole enchilada.
            val fetchColorDeferred = viewModel.viewModelScope.async (Dispatchers.Main)
            {
                repo.oneTimeDataFetchExample ()
            }

            // Wait for it. new button color
            val color = fetchColorDeferred.await()

            // Go back contains ui thread.
            // This causes us to rejoin the ui thread,
            withContext(Dispatchers.Main) {
                "Back.dataFecthCoRoutine() cnanged background color to $color".logInfo()

                // Update the button color in the view model.
                viewModel.backgroundColor = color

                "Back.dataFecthCoRoutine() finished.".logVerbose()
            }
        }
    }

    /**
     * Use this as an exampole of asking the repo for something
     * in the back, so we don't hang ui thread.
     *
     * Get a color on the back thread.
     *
     * To simulate how to have a long state
     * routine not hang up the uio threead.
     */
    @Suppress("unused")
    fun backgroundTaskCoroutine () {
        // Already active?
        if (bj?.isActive?:false) {
            return
        }

        // ***************************************************************** //
        "Back.backgroundTaskCoroutine Switching execution to the back thread ...".logInfo()
        // ***************************************************************** //

        "Back.backgroundTaskCoroutine started.".logVerbose()
        "Back.backgroundTaskCoroutine Setting button color ...".logVerbose()

        // Launch the coroutine and keep track of task.
        bj = viewModel.viewModelScope.launch {
            // The while (state) is the "flag" for continuous operation.
            // If the Job is cancelled, 'state' becomes false, and the loop exits.
            do {
                try {
                    // Perform the back work here.
                    // NOTE: Any suspending function (like delay) checks for cancellation automatically.
                    "Back.backgroundTaskCoroutine Calling ...".logVerbose()

                    // Execute background tasl.
                    repo.executeBackgroundTask()

                    // Wait 1 - 5 seconds
                    val time = (Consts.rndNum(MIN_DELAY, MAX_DELAY)).toLong()
                    "Back.backgroundTaskCoroutine Delaying: $time".logVerbose()

                    delay(time)
                }
                catch (e: CancellationException) {
                    // Re-throw CancellationException to propagate cancellation
                    "Back.backgroundTaskCoroutine CancellationException in back task: ${e.message}".logError(e)
                    viewModel.state = PAUSED
                    throw e
                }
                catch (e: Exception) {
                    viewModel.state = PAUSED
                    // Handle other exceptions (e.g., network error)
                    "Back.backgroundTaskCoroutine Error in back task: ${e.message}".logError(e)
                    "Back.backgroundTaskCoroutine Delaying 5000L millis".logVerbose()
                    delay(5000L) // Wait before retrying
                }
            } while (RUNNING == viewModel.state)

            // null out task, will re-create if startBackTask is called again.
            bj = null

            // We're done.
            "Back.backgroundTaskCoroutine finished.".logVerbose()
        }
    }
}