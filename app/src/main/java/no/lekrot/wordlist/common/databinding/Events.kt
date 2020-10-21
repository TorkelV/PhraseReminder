package no.lekrot.wordlist.common.databinding

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER

object Events {
    @JvmStatic
    val keyEventDone = KeyEvent(0, 0, KeyEvent.ACTION_DOWN, KEYCODE_ENTER, 0)
}
