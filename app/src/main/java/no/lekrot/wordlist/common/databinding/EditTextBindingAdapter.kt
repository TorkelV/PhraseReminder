package no.lekrot.wordlist.common.databinding

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import no.lekrot.wordlist.common.extensions.ViewExtension.hideKeyboard

object EditTextBindingAdapter {

    @JvmStatic
    @BindingAdapter("onFinishedEditing")
    fun EditText.setOnFinishedEditing(onFinishedEditing: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed) {
                    onFinishedEditing()
                    this.hideKeyboard()
                }
            }
            false
        }
    }
}
