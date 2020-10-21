package no.lekrot.wordlist.phrases.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SearchComponent {
    val searchString = MutableLiveData<String>("")
    private val _visible = MutableLiveData<Boolean>(false)
    val visible: LiveData<Boolean> get() = _visible

    fun show() {
        _visible.value = true
    }

    fun close() {
        searchString.value = ""
        _visible.value = false
    }
}