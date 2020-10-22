package no.lekrot.wordlist.common.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewExtension {
    fun View.hideKeyboard() {
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(this@hideKeyboard.windowToken, 0)
        }
    }
}
