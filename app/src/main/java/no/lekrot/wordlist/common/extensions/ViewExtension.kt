package no.lekrot.wordlist.common.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import no.lekrot.wordlist.common.databinding.EditTextBindingAdapter.setOnFinishedEditing

object ViewExtension {
    fun View.hideKeyboard() {
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(this@hideKeyboard.windowToken, 0)
        }
    }

}