package no.lekrot.wordlist.phrases.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import se.ingenuity.lives.Lives
import java.util.*

class AddPhraseComponent(private val onAddPhrase: (phrase: Phrase) -> Unit) {
    val phrase = MutableLiveData<String>("")
    val translation = MutableLiveData<String>("")
    private val _visible = MutableLiveData<Boolean>(false)
    val visible: LiveData<Boolean> get() = _visible
    val isValid = Lives.combineLatest(phrase, translation) { phrase, translation ->
        validate(phrase, translation)
    }

    private fun validate(phrase: String, translation: String): Boolean {
        return phrase.isNotBlank() && translation.isNotBlank()
    }

    fun submit(phrase: String, translation: String, groupId: String) {
        if (validate(phrase, translation)) {
            onAddPhrase(Phrase(UUID.randomUUID().toString(), phrase, translation, groupId))
        }
        this.close()
    }

    fun show() {
        _visible.value = true
    }

    fun close() {
        phrase.value = ""
        translation.value = ""
        _visible.value = false
    }
}
