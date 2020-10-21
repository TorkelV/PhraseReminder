package no.lekrot.wordlist.common.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import no.lekrot.wordlist.common.extensions.ViewExtension.hideKeyboard

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["closeKeyboardWith", "onClickCloseKeyboard"], requireAll = false)
    fun View.onClickCloseKeyboard(closeKeyboardWith: View, onClickCloseKeyboard: () -> Unit): Unit? {
        this.setOnClickListener {
            onClickCloseKeyboard()
            closeKeyboardWith.hideKeyboard()
            return@setOnClickListener
        }
        return null
    }

}