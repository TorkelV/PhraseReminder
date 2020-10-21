package no.lekrot.wordlist.phrases.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.lekrot.wordlist.phrases.domain.Phrase

class PhraseSettings(
    private val onDeletePhrase: (phrase: Phrase) -> Unit
) {
    private val _visible = MutableLiveData(false)
    val visible: LiveData<Boolean> get() = _visible
    fun show() {
        _visible.value = true
    }

    fun close() {
        _visible.value = false
    }

    fun deletePhrase(phrase: Phrase) {
        onDeletePhrase(phrase)
    }
}