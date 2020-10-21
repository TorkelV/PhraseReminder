package no.lekrot.wordlist.common.databinding

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter

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
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                        hideSoftInputFromWindow(this@setOnFinishedEditing.windowToken, 0)
                    }
                }
            }
            false
        }
    }
}
