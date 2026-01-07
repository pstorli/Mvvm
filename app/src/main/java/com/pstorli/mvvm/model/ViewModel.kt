package com.pstorli.mvvm.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.pstorli.mvvm.domain.State
import com.pstorli.mvvm.util.Prefs
import androidx.compose.ui.graphics.Color

class ViewModel (val app: Application) : AndroidViewModel (app)
{
    // /////////////////////////////////////////////////////////////////////////////////////////////
    // View Model Helper classes.
    // I like to only keep essential, data in this class.
    // I like to keep logic out, rather have helper classes perform logic.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // Used to run coroutines in the back.
    var vh    = VMHelper (this)

    // Preferences, initialize first
    // Questionable if these should be accessed through the repo.
    @Suppress("unused")
    var prefs = Prefs(app)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The state, use to determine what screen to display.
    // Use mutable state here
    // to link ui with data changes.
    var state by mutableStateOf<State>(State.PAUSED)

    // The app back color.
    var backgroundColor by mutableStateOf<Color>(Color.White)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Init things
    // /////////////////////////////////////////////////////////////////////////////////////////////
    init {
        // NOOP, FUBAR
    }
}


